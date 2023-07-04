package com.dilay.youtubemusic.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "FileData")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData extends BaseEntity {

    private String name ;
    private String type ;

    @Lob
    @Column(name = "filedata" , length = 10000000)
    private byte[] fileData;


}
