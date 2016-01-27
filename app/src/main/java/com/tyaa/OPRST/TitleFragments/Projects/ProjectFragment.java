package com.tyaa.OPRST.TitleFragments.Projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerFragment;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.TitleFragments.BaseTitleFragment;

/**
 * Created by Pavlo on 12/02/2015.
 */
public class ProjectFragment extends BaseTitleFragment {
    private static final String TAG = "ProjectFragment";
    public static final String PROJECT_NAME=TAG+".project_name";

    protected void makeVideo(Project...projects) {
        Bundle args=getArguments();
        if(args!=null) {
            Long id = args.getLong(CollectionActivity.ARG_TITLE_ID);
            mCallbacks.onFragmentNext(id, ProjectVideoContainer.class, projects);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project, container,false);

        Button no_comments= (Button) v.findViewById(R.id.project_no_comments_image);
        no_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.no_comments);
            }
        });
        Button interview= (Button) v.findViewById(R.id.project_interview_image);
        interview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.interview);
            }
        });
        Button our_videos=(Button)v.findViewById(R.id.project_our_videos_image) ;
        our_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.our_videos);
            }
        });
        Button cultural_life=(Button)v.findViewById(R.id.project_cultural_life_image) ;
        cultural_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.cultural_life);
            }
        });
        Button work_well_together=(Button)v.findViewById(R.id.project_work_well_together_image) ;
        work_well_together.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.work_well_together, Project.work_well_together2);
            }
        });
        Button soon=(Button)v.findViewById(R.id.project_soon_exaggerate_soon_image) ;
        soon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.soon);
            }
        });
        Button virtual_tours=(Button)v.findViewById(R.id.project_virtual_tours_image) ;
        virtual_tours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.virtual_tours);
            }
        });
        Button developments=(Button)v.findViewById(R.id.project_developments_image) ;
        developments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.developments);
            }
        });
        Button baby_surprise=(Button)v.findViewById(R.id.project_baby_surprise_image) ;
        baby_surprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.baby_surprise);
            }
        });
        Button full_version=(Button)v.findViewById(R.id.project_full_version_image) ;
        full_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.full_version);
            }
        });
        Button commercial_break=(Button)v.findViewById(R.id.project_commercial_break_image) ;
        commercial_break.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.commercial_break);
            }
        });
        Button this_is_mariupol=(Button)v.findViewById(R.id.project_this_is_Mariupol_image) ;
        this_is_mariupol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.this_is_mariupol);
            }
        });
        Button video_klq=(Button)v.findViewById(R.id.project_video_KVN_image) ;
        video_klq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.video_klq);
            }
        });
        Button video_reports=(Button)v.findViewById(R.id.project_video_reports_image) ;
        video_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.video_reports);
            }
        });
        Button night_clubs=(Button)v.findViewById(R.id.project_night_clubs_image) ;
        night_clubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.night_clubs);
            }
        });
        Button slag_blog=(Button)v.findViewById(R.id.project_slag_blog_image) ;
        slag_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.slag_blog);
            }
        });
        Button the_top_nine=(Button)v.findViewById(R.id.project_the_top_nine_and_three_quarters_image) ;
        the_top_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.the_top_nine);
            }
        });
        Button obviousness=(Button)v.findViewById(R.id.project_obviousness_image) ;
        obviousness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVideo(Project.obviousness);
            }
        });
        return v;
    }
}
