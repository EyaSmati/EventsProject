package tn.esprit.eventsproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.eventsproject.controllers.EventRestController;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.IEventServices;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IEventServices eventServices;

    @InjectMocks
    private EventRestController eventRestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventRestController).build();
    }

    @Test
    public void testAddParticipant() throws Exception {
        Participant participant = new Participant();
        participant.setNom("Doe");
        participant.setPrenom("John");
        when(eventServices.addParticipant(any(Participant.class))).thenReturn(participant);

        mockMvc.perform(post("/event/addPart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Doe\", \"prenom\":\"John\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Doe"))
                .andExpect(jsonPath("$.prenom").value("John"));
    }

    @Test
    public void testAddEventPart() throws Exception {
        Event event = new Event();
        event.setDescription("Event 1");
        when(eventServices.addAffectEvenParticipant(any(Event.class), any(Integer.class))).thenReturn(event);

        mockMvc.perform(post("/event/addEvent/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Event 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Event 1"));
    }

    @Test
    public void testAddEvent() throws Exception {
        Event event = new Event();
        event.setDescription("Event 1");
        when(eventServices.addAffectEvenParticipant(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/event/addEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Event 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Event 1"));
    }

    @Test
    public void testAddAffectLog() throws Exception {
        Logistics logistics = new Logistics();
        logistics.setDescription("Logistics 1");
        when(eventServices.addAffectLog(any(Logistics.class), any(String.class))).thenReturn(logistics);

        mockMvc.perform(put("/event/addAffectLog/Test Event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Logistics 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Logistics 1"));
    }


}