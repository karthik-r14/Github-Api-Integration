package com.thoughworks.githubapiintegration.view.commitInfo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.thoughworks.githubapiintegration.model.Person;
import com.thoughworks.githubapiintegration.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommitInfoActivity extends AppCompatActivity {

    @BindView(R.id.commit_list)
    ListView commitList;

    String total_count, incomplete_results;
    JSONArray items;
    ArrayList<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_info);
        ButterKnife.bind(this);
        persons = new ArrayList<>();
        new Mytask().execute("rails/rails+all");
    }

    class Mytask extends AsyncTask<String, Void, Void> {
        private ProgressDialog pDialog;
        boolean apiLimitExceeded = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CommitInfoActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection urlConnection;
            URL url;
            InputStream inputStream;

            try {
                url = new URL("https://api.github.com/search/commits?q=repo:" + params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/vnd.github.cloak-preview");

                //if you uncomment the following line GitHub API will not respond
                // urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.connect();
                //check for HTTP response
                int httpStatus = urlConnection.getResponseCode();

                //if HTTP response is 200 i.e. HTTP_OK read inputstream else read errorstream
                if (httpStatus != HttpURLConnection.HTTP_OK) {
                    inputStream = urlConnection.getErrorStream();
                    //print GitHub api hearder data
                    Map<String, List<String>> map = urlConnection.getHeaderFields();
                    System.out.println("Printing Response Header...\n");
                    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                        System.out.println(entry.getKey()
                                + " : " + entry.getValue());
                    }
                } else {
                    inputStream = urlConnection.getInputStream();
                }

                //read inputstream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp, response = "";
                while ((temp = bufferedReader.readLine()) != null) {
                    response += temp;
                }

                System.out.println("Result" + response);
                //GitHub api has limit to access over http.
                //Api rate limit is 10req/min for unauthenticated user and 30req/min is for authenticated user
                boolean apiLimitExceeded = false;

                if (response.contains("API rate limit exceeded")) {
                    apiLimitExceeded = true;
                } else {
                    //convert data string into JSONObject
                    JSONObject obj = (JSONObject) new JSONTokener(response).nextValue();
                    items = obj.getJSONArray("items");

                    //total result count and result status
                    total_count = obj.getString("total_count");
                    incomplete_results = obj.getString("incomplete_results");
                }

                //System.out.println("Items" + items);

                setUpPersonList();

                urlConnection.disconnect();
            } catch (MalformedURLException | ProtocolException | JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void setUpPersonList() throws JSONException {
            for (int i = 0; i < 25; ++i) {
                JSONObject jsonObject = items.getJSONObject(i);

                String personName = jsonObject.getJSONObject("commit").getJSONObject("author").getString("name");
                String commitMessage = jsonObject.getJSONObject("commit").getString("message");
                String commitSha = jsonObject.getString("sha");
                String imageUrl = jsonObject.getJSONObject("author").getString("avatar_url");

                Person person = new Person(personName, commitMessage, commitSha, imageUrl);
                persons.add(person);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            CustomAdapter adapter = new CustomAdapter(getApplicationContext(), persons);
            commitList.setAdapter(adapter);
        }
    }
}