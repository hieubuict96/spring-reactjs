package com.springbootapp.payload.request.user;

public class SigninWithFacebookDto {
  private Data data;

  public Data getData() {
    return data;
  }

  public class Data {
    private String name;

    private String userID;

    private Picture picture;

    public String getName() {
      return name;
    }

    public String getUserID() {
      return userID;
    }

    public Picture getPicture() {
      return picture;
    }

    public class Picture {
      private Data2 data;

      public Data2 getData() {
        return data;
      }

      public class Data2 {
        private String url;

        public String getUrl() {
          return url;
        }
      }
    }
  }
}
