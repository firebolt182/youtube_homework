package org.javaacademy.hibernate;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.javaacademy.hibernate.entity.Comment;
import org.javaacademy.hibernate.entity.User;
import org.javaacademy.hibernate.entity.Video;
import java.util.Properties;

public class Runner {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/youtube");
        properties.put("hibernate.connection.username", "postgres");
        properties.put("hibernate.connection.password", "postgres");
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put(Environment.SHOW_SQL, true);
        properties.put(Environment.FORMAT_SQL, true);

        @Cleanup SessionFactory sessionFactory = new Configuration().addProperties(properties)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Video.class)
                .addAnnotatedClass(Comment.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User john = new User("John");
        session.persist(john);
        User rick = new User("Rick");
        session.persist(rick);
        Video johnVideo1 = new Video("My first interview", "description#1", john);
        session.persist(johnVideo1);
        Video johnVideo2 = new Video("My second interview", "description#2", john);
        session.persist(johnVideo2);
        Comment rickComment1 = new Comment(johnVideo1, rick, "cool interview");
        session.persist(rickComment1);
        session.getTransaction().commit();
        session.clear();

        User johnUser = session.find(User.class, 1);
        Video findVideo = johnUser.getVideos().stream()
                .filter(video -> video.getName().equals("My first interview"))
                .findFirst()
                .orElseThrow();
        Comment findComment = findVideo.getComments().stream()
                .findFirst()
                .orElseThrow();
        System.out.println(findComment);
    }
}