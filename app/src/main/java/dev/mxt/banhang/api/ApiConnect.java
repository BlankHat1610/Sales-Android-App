package dev.mxt.banhang.api;

public class ApiConnect {
    private static String host = "http://192.168.1.4/";
    private static String apiUrl = host + "Laravel_Sem1_Y2/public/api/";
    private static String subfolderImageUrl = "";
    private static String imageUrl = host + "Laravel_Sem1_Y2/public/uploads/";

    public static void setSubfolderImageUrl(String yearSubfolder, String monthSubfolder, String daySubfolder) {
        ApiConnect.subfolderImageUrl = yearSubfolder + "/" + monthSubfolder + "/" + daySubfolder + "/";
    }

    public static String getImageUrl() {
        return imageUrl;
    }

    public static String getHost() {
        return host;
    }

    public static String getSubfolderImageUrl() {
        return subfolderImageUrl;
    }

    public static String getApiUrl() {
        return apiUrl;
    }
}
