package com.tyaa.OPRST.DataService;

import android.os.Parcel;
import android.os.Parcelable;

import com.tyaa.OPRST.TitleFragments.Projects.Project;

import java.io.Serializable;

/**
 * Created by Pavlo on 12/07/2015.
 */
public class MsgHolder implements Serializable,Parcelable {
    public MsgHolder(){}
    private String tag;
    private String extra;
        private String id;
        private String width;
        private String height;
        private String urlVote;
        private Project[] projects;
    private Method mMethod;

        public String getUrlVote() {
                return urlVote;
        }

        public void setUrlVote(String urlVote) {
                this.urlVote = urlVote;
        }

        public Project[] getProjects() {
                return projects;
        }

        public void setProjects(Project[] projects) {
                this.projects = projects;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getId() {
                return id;
        }

        public String getWidth() {
                return width;
        }

        public String getHeight() {
                return height;
        }

        public void setWidth(int width) {
                this.width = String.valueOf(width);
        }

        public void setHeight(int height) {
                this.height = String.valueOf(height);
        }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Method getMethod() {
        return mMethod;
    }

    public void setMethod(Method method) {
        mMethod = method;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(tag);
            dest.writeString(extra);
            dest.writeString(id);
            dest.writeString(width);
            dest.writeString(height);
            dest.writeString(urlVote);
            dest.writeSerializable(projects);
            dest.writeValue(mMethod);
        }
        // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
        public static Creator<MsgHolder> CREATOR = new Creator<MsgHolder>() {
                public MsgHolder createFromParcel(Parcel in) {
                        return new MsgHolder(in);
                }

                public MsgHolder[] newArray(int size) {
                        return new MsgHolder[size];
                }
        };

        // example constructor that takes a Parcel and gives you an object populated with it's values
        protected MsgHolder(Parcel src) {
            tag=src.readString();
            extra=src.readString();
            id = src.readString();
            width = src.readString();
            height = src.readString();
            urlVote = src.readString();
            projects = (Project[]) src.readSerializable();
            mMethod= (Method) src.readValue(Method.class.getClassLoader());
        }
}
