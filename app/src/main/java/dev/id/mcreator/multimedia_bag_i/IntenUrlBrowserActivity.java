package dev.id.mcreator.multimedia_bag_i;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntenUrlBrowserActivity extends AppCompatActivity {

    @BindView(R.id.edtUrl)
    EditText edtUrl;
    @BindView(R.id.btnAksesLink)
    Button btnAksesLink;

    String getUrl;
    String layoutDefaultURL = "http://";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inten_url_browser);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAksesLink)
    public void onViewClicked() {
        getUrl = layoutDefaultURL + edtUrl.getText().toString() + "/";
        Toast.makeText(this, "URL " + getUrl, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getUrl)));
    }
}
