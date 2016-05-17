package in.sling.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rotvic on 5/16/2016.
 */
public class Token {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private UserPopulated user;

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The user
     */
    public UserPopulated getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(UserPopulated user) {
        this.user = user;
    }

}
