package realsoft.hotel.entity;

import java.util.List;
public class Room {

   private Long roomId;
   private Account user;
   private Type roomType;
   private boolean isRented;
   private List<Privilege> privileges;

}
