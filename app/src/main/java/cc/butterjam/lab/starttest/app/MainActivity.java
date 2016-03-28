package cc.butterjam.lab.starttest.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import javax.inject.Inject;

public class MainActivity extends Activity {

    private ListView listWords;
    private TextView textInput;
    private TextView textNotice;
    private Button buttonInput;

    @Inject
    WordValidator[] validators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make references to UI elements
        listWords = (ListView) findViewById(R.id.list_words);
        textInput = (TextView) findViewById(R.id.text_input);
        textNotice = (TextView) findViewById(R.id.text_notice);
        buttonInput = (Button) findViewById(R.id.button_input);

        // Inject this activity's dependencies
        ((MainApplication) getApplication()).componentManager.injectComponentsInto(this);

        // Initialization
        listWordsAdapter = new ListWordsAdapter();
        buttonInput.setOnClickListener(OnButtonInputClicked);
    }

    private class ListWordsAdapter extends ArrayAdapter<String> {
        ListWordsAdapter() {
            super(MainActivity.this, R.layout.listview_item);
            setNotifyOnChange(true);
            listWords.setAdapter(this);
        }
    }

    private ListWordsAdapter listWordsAdapter;

    private View.OnClickListener OnButtonInputClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addWordToList(getInput());
            clearInput();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_all:
                listWordsAdapter.clear();
                clearInput();
                clearNotice();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Validate word input and show it on the list view
     *
     * @param word word input
     */
    private void addWordToList(String word) {
        clearNotice();

        ValidatedWord validated = null;
        for (WordValidator validator : validators) {
            if (validated == null) {
                validated = validator.validate(word);
            } else {
                validated = validator.validate(validated);
            }
            if (!validated.isValid) {
                showNotice(validated.errorMessage);
                return;
            }
        }
        if (validated == null) {
            return;
        }

        listWordsAdapter.add(validated.word);
        listWords.smoothScrollToPosition(listWordsAdapter.getCount() - 1);
    }


    public void showNotice(String message) {
        textNotice.setText(message);
    }

    public void clearNotice() {
        textNotice.setText("");
    }

    public String getInput() {
        return textInput.getText().toString();
    }

    public void clearInput() {
        textInput.setText("");
    }


}
