package com.revature.shms.servicetests;

import com.revature.shms.enums.ReservationStatus;
import com.revature.shms.models.Reservation;
import com.revature.shms.models.User;
import com.revature.shms.repositories.ReservationRepository;
import com.revature.shms.services.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
	@InjectMocks ReservationService reservationService ;
	@Mock ReservationRepository reservationRepository;

	@Test
	public void returnAllReservationsTest(){
		List<Reservation> test = new ArrayList<>();
		Reservation reservation = new Reservation();
		reservation.setReservationID(1);
		test.add(reservation);
		when(reservationRepository.findAll()).thenReturn(test);
		assertEquals(test, reservationService.findAll());
	}
//	@Test
//	public void reserveApprove(){
//		Reservation reservation = new Reservation();
//		when(reservationRepository.approveReservationByEmployee_EmployeeId(1)).thenReturn(reservation);
//		assertEquals(reservationService.approveReservation((1)), reservation);
//	}
//
//	@Test
//	public void denyReservation(){
//		Reservation reservation = new Reservation();
//		when(reservationRepository.denyReservationByEmployee_EmployeeId(1)).thenReturn(reservation);
//		assertEquals(reservationService.denyReservation((1)), reservation);
//	}

	@Test
	public void getSpecificReservation() throws NotFound {
		int id = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(id);
		when(reservationRepository.findByUserReserve_UserID(1)).thenReturn(java.util.Optional.of(reservation));
		assertEquals(id, reservationService.findReservationOfUser("1").getReservationID());
	}

	@Test
	public void createUserTest(){
		Reservation reservation = new Reservation();
		reservation.setStatus(ReservationStatus.APPROVED.toString());
		when(reservationRepository.save(reservation)).thenReturn(reservation);
		assertEquals(reservation, reservationService.createReservation(reservation));
	}

	@Test
	public void deleteUserTest(){
		reservationService.deleteReservation(1231245);
		verify(reservationRepository,times(1)).deleteByUserReserve_UserID(anyInt());
	}

    @Test void changeStatusOfReservationTest(){
        Reservation reservation = new Reservation();
        reservation.setReservationID(1);
        Reservation update = new Reservation();
        reservation.setStatus(ReservationStatus.APPROVED.toString());
        update.setStatus(ReservationStatus.CANCELLED.toString());
        when(reservationRepository.save(any())).thenReturn(reservation);
        assertEquals(reservation, reservationService.changeStatusOfReservation(update));
    }

	@Test void changeDateOfReservationTest(){
		Reservation reservation = new Reservation();
		reservation.setReservationID(1);
		Reservation update = new Reservation();
		reservation.setStatus(ReservationStatus.APPROVED.toString());
		update.setStatus(ReservationStatus.CANCELLED.toString());
		when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.changeDateOfReservation(update));
	}

	@Test void getReservationWithReservationIdTest () throws NotFound {
		int id = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(id);
		when(reservationRepository.findByReservationID(1)).thenReturn(java.util.Optional.of(reservation));
		assertEquals(id, reservationService.findReservationWithReservationId("1").getReservationID());
	}

	@Test
	public void reserveTest(){
		Reservation reservation = new Reservation();
		User user = new User();
		String date = "12/11/1997";
		when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.setReservation(user, date, date));
	}

	@Test
	public void gettersSetters(){
		ReservationRepository reservationRepository = null;
		ReservationService reservationService = new ReservationService();
		reservationService.setReservationRepository(reservationRepository);
		assertNull(reservationService.getReservationRepository());
		reservationService.setReservationRepository(null);
	}
}
