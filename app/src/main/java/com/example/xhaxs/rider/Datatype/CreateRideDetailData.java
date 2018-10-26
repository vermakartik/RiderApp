package com.example.xhaxs.rider.Datatype;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateRideDetailData implements Parcelable {
    private static final String LOG_CLASS = CreateRideDetailData.class.getName();

    private static final String RIDE_OWNER_STRING = "ride_owner";
    private static final String TO_LOC_STRING = "to_loc";
    private static final String FROM_LOC_STRING = "from_loc";
    private static final String RIDE_USER_ARRAY_STRING = "ride_users";
    private static final String JOURNEY_TIME_STRING = "journey_time";
    private static final String MAX_ACC_STRING = "max_accomodation";

    private String rideID;

    public String getRideID() {
        return rideID;
    }

    public void setRideID(String rideID) {
        this.rideID = rideID;
    }

    private UserSumData rideOwner;
    private PlaceData toLoc;
    private PlaceData fromLoc;
    private ArrayList<UserSumData> rideUsers;
    private long journeyTime;
    private int maxAccomodation;

    public CreateRideDetailData(Parcel in){
        this.rideID = in.readString();
        rideOwner = in.readParcelable(UserSumData.class.getClassLoader());
        toLoc = in.readParcelable(PlaceData.class.getClassLoader());
        fromLoc = in.readParcelable(PlaceData.class.getClassLoader());
        this.rideUsers = in.readArrayList(UserSumData.class.getClassLoader());
        journeyTime = in.readLong();
        maxAccomodation = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rideID);
        dest.writeParcelable(rideOwner, flags);
        dest.writeParcelable(toLoc, flags);dest.writeParcelable(fromLoc, flags);
        dest.writeList(rideUsers);
        dest.writeLong(journeyTime);
        dest.writeInt(maxAccomodation);
    }

    public CreateRideDetailData(UserSumData rideOwner, PlaceData toLoc, PlaceData fromLoc, Calendar journeyTime, int maxAccomodation) {
        this.rideID = "";
        this.rideUsers = new ArrayList<>();
        this.rideUsers.add(rideOwner);
        this.rideOwner = rideOwner;
        this.toLoc = toLoc;
        this.fromLoc = fromLoc;
        this.journeyTime = journeyTime.getTimeInMillis();
        this.maxAccomodation = maxAccomodation;
    }

    public static final Creator<CreateRideDetailData> CREATOR = new Creator<CreateRideDetailData>() {
        public CreateRideDetailData createFromParcel(Parcel source) {
            return new CreateRideDetailData(source);
        }
        public CreateRideDetailData[] newArray(int size) {
            return new CreateRideDetailData[size];
        }
    };

    public PlaceData getToLoc() {
        return toLoc;
    }

    public void setToLoc(PlaceData toLoc) {
        this.toLoc = toLoc;
    }

    public UserSumData getRideOwner() {
        return rideOwner;
    }

    public void setRideOwner(UserSumData rideOwner) {
        this.rideOwner = rideOwner;
    }

    public PlaceData getFromLoc() {
        return fromLoc;
    }

    public void setFromLoc(PlaceData fromLoc) {
        this.fromLoc = fromLoc;
    }

    public Calendar getJourneyTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(journeyTime);
        return calendar;
    }

    public void setJourneyTime(Calendar journeyTime) {
        this.journeyTime = journeyTime.getTimeInMillis();
    }

    public ArrayList<UserSumData> getRideUsers() {
        return rideUsers;
    }

    public void setRideUsers(ArrayList<UserSumData> rideUsers) {
        this.rideUsers = rideUsers;
    }

    public int getMaxAccomodation() {
        return maxAccomodation;
    }

    public void setMaxAccomodation(int maxAccomodation) {
        this.maxAccomodation = maxAccomodation;
    }

    public int getCurAccomodation() {
        return (this.rideUsers.size());
    }

    @Override
    public String toString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(journeyTime);
        return "<CreateRideDetailDate -> " + "\n"
                + "From: " + fromLoc.getName() + "\n"
                + "To: " + toLoc.getName() + "\n"
                + "Time: " + DateFormat
                .getDateTimeInstance(DateFormat.SHORT
                        , DateFormat.SHORT)
                .format(calendar.getTime()) + "\n"
                + "maxAccomodation: " + maxAccomodation
                + "> ";
    }

    public boolean addUser(UserSumData cUserData) {
        if(cUserData.getUid() == rideOwner.getUid()) return false;
        if(rideUsers.size() == maxAccomodation) return false;
        for(int i = 0; i < rideUsers.size(); ++i){
            if(cUserData.getUid() == rideUsers.get(i).getUid()) return false;
        }
        rideUsers.add(cUserData);
        return true;
    }

    public boolean removeUser(UserSumData cUserData){
        if(cUserData.getUid() == rideOwner.getUid()) return false;
        if(rideUsers.size() == 1) return false;
        for(int i = 0; i < rideUsers.size(); ++i){
            if(cUserData.getUid() == rideUsers.get(i).getUid()){
                rideUsers.remove(i);
                return true;
            }
        }
        return false;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RIDE_OWNER_STRING, rideOwner.toMap());
        map.put(FROM_LOC_STRING, fromLoc.toMap());
        map.put(TO_LOC_STRING, toLoc.toMap());
        map.put(JOURNEY_TIME_STRING, journeyTime);
        map.put(MAX_ACC_STRING, maxAccomodation);
        HashMap<String, Object> rideUsersHash = new HashMap<>();
        for(int i = 0; i < rideUsers.size(); ++i){
            rideUsersHash.put(Integer.toString(i), rideUsers.get(i).toMap());
        }
        map.put(RIDE_USER_ARRAY_STRING, rideUsersHash);
        Log.d("000","--------------------------");
        Log.d(LOG_CLASS, map.toString());
        Log.d("000","--------------------------");
        return map;
    }

    public CreateRideDetailData(String key, Map<String, Object> map){
        this.rideID = key;

        Log.d(this.getClass().getName(), "************** CALLING MAP FOR -- RIDE OWNER -- CREATE RIDE DETAIL");
        this.rideOwner = new UserSumData((Map<String, Object>)map.get(RIDE_OWNER_STRING));

        Log.d(this.getClass().getName(), "************** CALLING MAP FOR -- FROM PLACE -- CREATE RIDE DETAIL");
        this.fromLoc = new PlaceData((Map<String, Object>)map.get(FROM_LOC_STRING));

        Log.d(this.getClass().getName(), "************** CALLING MAP FOR -- TO PLACE -- CREATE RIDE DETAIL");
        this.toLoc = new PlaceData((Map<String, Object>)map.get(TO_LOC_STRING));

        Log.d(this.getClass().getName(), "************** CALLING MAP FOR -- MAX ACCOMODATION -- CREATE RIDE DETAIL");
        this.maxAccomodation = Integer.parseInt(map.get(MAX_ACC_STRING).toString());

        Log.d(this.getClass().getName(), "************** CALLING MAP FOR -- JOURNEY TIME -- CREATE RIDE DETAIL");
        this.journeyTime = Long.parseLong(map.get(JOURNEY_TIME_STRING).toString());

        Log.d(this.getClass().getName(), "************** CALLING MAP FOR -- USER ARRAY DATA -- CREATE RIDE DETAIL");

        ArrayList<Map<String, Object>> ru = (ArrayList<Map<String,Object>>) map.get(RIDE_USER_ARRAY_STRING);

        this.rideUsers = new ArrayList<>();

        for(int i = 0; i < ru.size(); ++i){
            UserSumData temp = new UserSumData(ru.get(i));
            if(temp.getUid() != rideOwner.getUid()){
                this.rideUsers.add(temp);
            }
        }
    }
}