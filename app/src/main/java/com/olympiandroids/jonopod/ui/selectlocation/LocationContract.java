package com.olympiandroids.jonopod.ui.selectlocation;

public class LocationContract {
    //For Division and Districts
    public static final String DIVISION_NAME = "divisionName";
    public static final String DISTRICT_NAME = "districtName";
    public static final String IS_DIVISION = "isDivision";

    //For Upazilas
    public static final String UPAZILLA_NAME = "upazilaName";
    public static final String UNION_NAME = "unionName";


    //Location Categories
    public static final int CATEGORY_DIVISION = 1;
    public static final int CATEGORY_DISTRICT = 2;
    public static final int CATEGORY_UPAZILA = 3;
    public static final int CATEGORY_WORD_UNION = 4;

    //Firestore Collection Paths
    public static final String FIRESTORE_ROOT_LOCATIONS = "locations";
    public static final String FIRESTORE_UPAZILAS = "upazilas";
    public static final String FIRESTORE_UNIONS = "unions";
}
