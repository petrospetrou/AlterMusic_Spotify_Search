package com.altmusic.altermusic;

import com.altmusic.altermusic.service.SpotifyServiceImplementation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class AlterMusicApplication {
    public static void main(String[] args) {SpringApplication.run(AlterMusicApplication.class, args);}

}
