package com.vily.progressbar.adapt;

import com.chad.library.adapter.base.entity.SectionEntity;

public class MySection  extends SectionEntity<Student> {


    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MySection(Student student) {
        super(student);
    }
}
