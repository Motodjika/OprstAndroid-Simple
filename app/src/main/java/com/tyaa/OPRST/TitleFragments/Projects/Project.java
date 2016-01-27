package com.tyaa.OPRST.TitleFragments.Projects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Pavlo on 12/06/2015.
 */
public enum Project implements Serializable,Parcelable {
    no_comments(19),
    interview(20),
    our_videos(21),
    cultural_life(22),
    work_well_together(23),
    soon(24),
    work_well_together2(25),
    virtual_tours(26),
    developments(27),
    baby_surprise(28),
    full_version(29),
    commercial_break(30),
    this_is_mariupol(31),
    video_klq(32),
    video_reports(33),
    night_clubs(34),
    slag_blog(35),
    the_top_nine(36),
    obviousness(37);
    private int value;
    Project(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value=value;
    }
    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {

        public Project createFromParcel(Parcel in) {
            Project project = Project.values()[in.readInt()];
            project.setValue(in.readInt());
            return project;
        }

        public Project[] newArray(int size) {
            return new Project[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(ordinal());
        out.writeInt(value);
    }
}
