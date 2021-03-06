package com.codeschool.Utils;

public class Constants {
    //All Tech news
    public static final String TECH_NEWS_BASE_URL ="http://newsapi.org/";
    public static final String TECH_NEWS_FILTERED = "/v2/top-headlines?country=us&category=technology&apiKey=42163898b9524d94b97d876f947ce134";

    //Server URL
//    public static final String SERVER_URL="https://code-school-backend.herokuapp.com/";
    public static final String SERVER_URL="http://10.0.0.217:3000/";
    //Realtime
//    public static final String REALTIME_SERVER_URL="https://code-school-backend.herokuapp.com/multiplayer/find";
    public static final String REALTIME_SERVER_URL="http://10.0.0.217:3000/multiplayer/find";

    //Shared Preferences
    public static final String USERDATA = "userdata";
    public static final String SESSIONDATA = "sessiondata";
    public static final String COURSEENROLLED = "courseenrolled";

    //DataBase
    public static final String DBName = "CodeSchool";
    public static final String TABLE_COURSEDATA = "CourseData";

}
