package engg1420_project.universitymanagementsystem;

import engg1420_project.universitymanagementsystem.projectClasses.User;

public class Registration {
    private User user;
    private Event event;

    public Registration(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
