package pl.turistica.service;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.turistica.model.Trip;
import pl.turistica.model.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;

@Service
public class EnrollService {

    @Autowired
    EmailService emailService;

    public void sendEmailThread(User user, Trip trip, int tripId, boolean isEnrolling){
        String subject;
        String template;
        if(isEnrolling) {
            subject = "Zapisałeś się na wyjazd!";
            template = "enroll-email-template.ftl";
        }else{
            subject = "Wypisałeś się z wyjazdu!";
            template = "unsubscribe-email-template.ftl";
        }
        new Thread(() -> {
            try {
                emailService.sendMessage(user.getEmail(), subject, getTemplateModel(trip, user, tripId),template);
            } catch (MessagingException | IOException | TemplateException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private HashMap<String, Object> getTemplateModel(Trip trip, User user, int tripId){
        HashMap<String, Object> details = new HashMap<>();
        details.put("firstName", user.getName());
        details.put("lastName", user.getLastName());
        details.put("tripName", trip.getName());
        details.put("beginDate", trip.getBeginDate());
        details.put("endDate", trip.getEndDate());
        details.put("pricePerPerson", trip.getPricePerPerson());
        details.put("tripId", tripId);
        return details;
    }
}
