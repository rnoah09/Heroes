package com.example.heroes;

import android.os.Parcelable;
import android.os.Parcel;

public class Hero implements Parcelable, Comparable<Hero>{

    private String name;
    private String description;
    private String superpower;
    private String ranking;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking.equals(ranking);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.superpower);
        dest.writeString(this.ranking);
        dest.writeString(this.image);
    }

    public Hero() {
    }

    protected Hero(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.superpower = in.readString();
        this.ranking = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Hero> CREATOR = new Parcelable.Creator<Hero>() {
        @Override
        public Hero createFromParcel(Parcel source) {
            return new Hero(source);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };

    @Override
    public int compareTo(Hero hero) {
        if(Integer.parseInt(ranking) > Integer.parseInt(hero.getRanking())){
            return 1;
        }
        else if (Integer.parseInt(ranking) < Integer.parseInt(hero.getRanking())){
            return -1;
        }
        return 0;
    }
}
