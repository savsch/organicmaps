package app.organicmaps.sync;

import android.content.Context;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import app.organicmaps.R;

import java.util.Objects;

// {"rt": string, "em": string}

public class GoogleDriveAuth extends AuthState
{
  private static final String KEY_REFRESH_TOKEN = "rt";
  private static final String KEY_EMAIL = "em";

  private final String mRefreshToken;
  private final String mEmail;

  public GoogleDriveAuth(JSONObject authStateJson) throws JSONException
  {
    super(authStateJson);
    mRefreshToken = authStateJson.getString(KEY_REFRESH_TOKEN);
    mEmail = authStateJson.getString(KEY_EMAIL);
  }

  @Override
  public JSONObject toJson() throws JSONException
  {
    JSONObject json = new JSONObject();
    json.put(KEY_REFRESH_TOKEN, mRefreshToken);
    json.put(KEY_EMAIL, mEmail);
    return json;
  }

  @Override
  public boolean equals(AuthState other)
  {
    if (other instanceof GoogleDriveAuth)
      return Objects.equals(mEmail, ((GoogleDriveAuth) other).mEmail);
    return false;
  }

  @Override
  public String getUsername()
  {
    return mEmail;
  }

  @Override
  public String getBackendInfo(Context context)
  {
    return context.getString(R.string.google_drive);
  }

  @Nullable
  @Override
  public Long getExpiryTimestamp()
  {
    return null;
  }

  public static GoogleDriveAuth fromTokenAndEmail(String refreshToken, String email) throws JSONException
  {
    JSONObject json = new JSONObject();
    json.put(KEY_REFRESH_TOKEN, refreshToken);
    json.put(KEY_EMAIL, email);
    return new GoogleDriveAuth(json);
  }
}

/*
* Use the refreshToken this way:
POST /oauth2/v4/token HTTP/2
Host: www.googleapis.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 228
Accept: application/json

client_id=215362850630-l9j0opuu675mu14ri7b9mnsi28nroh2e.apps.googleusercontent.com&grant_type=refresh_token&refresh_token=whatever

* Response:
{
  "access_token": "whatever",
  "expires_in": 3599,
  "scope": "https://www.googleapis.com/auth/drive.file",
  "token_type": "Bearer"
}
* */