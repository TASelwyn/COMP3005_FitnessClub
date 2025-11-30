package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
