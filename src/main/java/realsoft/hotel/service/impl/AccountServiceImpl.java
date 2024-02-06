package realsoft.hotel.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import realsoft.hotel.dao.AccountDAO;
import realsoft.hotel.dao.OrderDAO;
import realsoft.hotel.dao.RoleDAO;
import realsoft.hotel.dao.RoomDAO;
import realsoft.hotel.dto.AccountDTO;
import realsoft.hotel.dto.AccountDTOForAdding;
import realsoft.hotel.dto.AccountDTOForUpdating;
import realsoft.hotel.dto.RoomDTO;
import realsoft.hotel.entity.Account;
import realsoft.hotel.entity.Orders;
import realsoft.hotel.entity.Role;
import realsoft.hotel.entity.Room;
import realsoft.hotel.exception.NoResourceFoundException;
import realsoft.hotel.mapper.AccountMapper;
import realsoft.hotel.mapper.RoomMapper;

import realsoft.hotel.service.AccountService;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {


    private final AccountDAO accountDAO;
    private final RoomDAO roomDAO;
    private final OrderDAO orderDAO;
    private PasswordEncoder passwordEncoder;
    private RoleDAO roleDAO;
    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO, RoomDAO roomDAO, OrderDAO orderDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.accountDAO = accountDAO;
        this.roomDAO = roomDAO;
        this.orderDAO = orderDAO;
        this.roleDAO = roleDAO;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<List<AccountDTOForAdding>> getAllAccounts() {
        List<Account> accounts = accountDAO.findAllAccounts();
        for (Account account : accounts) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        return ResponseEntity.ok(accounts.stream().map(AccountMapper::toDTOWithoutRooms).toList());
    }

    @Override
    public ResponseEntity<List<AccountDTO>> getAllAccountsWithRoom() {
        List<AccountDTO> accounts = accountDAO.findAllAccountsWithRoom().stream().map(AccountMapper::toDTO).toList();
        return ResponseEntity.ok(accounts);
    }

    @Override
    public ResponseEntity<AccountDTO> getAccountById(Long id) {
        List<Account> account = accountDAO.findAccountById(id);
        if (account.isEmpty()){
            throw new NoResourceFoundException("No such data Found");
        }
        AccountDTO accountDTO = AccountMapper.toDTO(account.get(0));
        return ResponseEntity.ok(accountDTO);
    }


    @Override
    public ResponseEntity<List<RoomDTO>> getCurrentUsersRooms() {
        Account account = accountDAO.findAccountByUsernameWithRooms(getCurrentUser());
        if (account == null){
            return null;
        }
        return ResponseEntity.ok(account.getRooms().stream().map(RoomMapper::toDto).toList());
    }

    //TODO listni test qilib korish tepadagi methodda AccountDTO ichidagi get qanday ishlayapti

    @Override
    public ResponseEntity<String> addAccount(AccountDTOForAdding accountDTOForAdding) {
        if (accountDTOForAdding.name().isEmpty() || accountDTOForAdding.email().isEmpty() || accountDTOForAdding.password().isEmpty()){
            throw new NullPointerException("Please fill the blanks before adding!");
        }
        Account account = accountDAO.findByEmail(accountDTOForAdding.email());
        if (account != null){
            return new ResponseEntity<>("The account with such email already exists", HttpStatus.BAD_REQUEST);
        }
        if (accountDAO.findByName(accountDTOForAdding.name()) != null){
            return new ResponseEntity<>("The account with such username already exists!",HttpStatus.BAD_REQUEST);
        }
        Account account1 = AccountMapper.toEntityForAdding(accountDTOForAdding);
        List<Role> roles = roleDAO.findByName("ROLE_USER");
        account1.setRoles(roles);
        account1.setPassword(passwordEncoder.encode(accountDTOForAdding.password()));
        accountDAO.addAccount(account1);
        roleDAO.insertRoleUser(account1.getEmail());
        return new ResponseEntity<>("Account added successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateAccount(AccountDTOForUpdating accountDTO) {
        System.out.println(getCurrentUser()+ "-----------------------------");
        Account accounts = accountDAO.findByName(getCurrentUser());
        if (accounts == null){
            return new ResponseEntity<>("No user with such name found :(",HttpStatus.BAD_REQUEST);
        }

        accounts.setName(accountDTO.name());
        accounts.setEmail(accountDTO.email());
        accounts.setPassword(passwordEncoder.encode(accountDTO.password()));
        accounts.setPhoneNumber(accountDTO.phoneNumber());
        accountDAO.updateAccount(accounts);
        return new ResponseEntity<>("Account with username "+accountDTO.name()+" has been updated successfully!",HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteAccount(Long id) {
        List<Orders> orders = orderDAO.getAllOrders();
        for (Orders order : orders) {
            if (order.getAccountId().equals(id)){
                List<Orders> orders1 = orderDAO.getOrderById(order.getId());
                Orders order2 = orders1.get(0);
                if (order2.getToDate().isAfter(LocalTime.now())){
                    return new ResponseEntity<>("You cannot delete User while he has rented room!",HttpStatus.FORBIDDEN);
                }
            }

        }
        for (Orders order : orders) {
            if (order.getAccountId().equals(id)){
                List<Orders> orders1 = orderDAO.getOrderById(order.getId());
                Orders order2 = orders1.get(0);
                orderDAO.deleteOrder(order2.getId());
            }

        }
        accountDAO.deleteAccount(id);
        return null;
    }

    @Override
    public ResponseEntity<List<RoomDTO>> getAllAvailableRooms() {
        List<RoomDTO> rooms = accountDAO.findAllAvailableRooms().stream().map(RoomMapper::toDto).toList();
        return ResponseEntity.ok(rooms);
    }


    @Override
    public ResponseEntity<String> deleteAccountsRentedRoom(Long id) {
        Room room = roomDAO.findRoomById(id);
        Account account = accountDAO.findByName(getCurrentUser());
        if (room == null){
            return new ResponseEntity<>("There is no room with ID:"+id+ " in our hotel",HttpStatus.NOT_FOUND);
        }
        if (Objects.equals(room.getAccountId(), account.getId())) {
            RoomDTO roomDTO = RoomMapper.toDto(accountDAO.deleteAccountsRentedRoom(id));
            return new ResponseEntity<>("Room with number: "+id+" is free now",HttpStatus.OK);
        }
        return new ResponseEntity<>("You haven't rented room with ID:"+id,HttpStatus.FORBIDDEN);
    }

    @Override
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return "something is went wrong!";

    }

    @Override
    public ResponseEntity<String> addRoleAdmin(String username) {
        Account accounts = accountDAO.findByName(username);
        if (accounts == null){
            return new ResponseEntity<>("There is no user with such id",HttpStatus.NOT_FOUND);
        }
        Role role = roleDAO.findByName("ROLE_ADMIN").get(0);
        accounts.getRoles().add(role);
        roleDAO.insertRoleAdmin(accounts.getEmail());
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Account account = accountDAO.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("cannot load user:");
        }
        System.out.println("account:" + account);
        List<Role> roles = roleDAO.findAllRolesByUserId(account.getId());

        if (roles.isEmpty()){
            throw new IllegalArgumentException("Role not found");
        }

        System.out.println("role" + roles);

        List<SimpleGrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).toList();


        return new User(account.getName(),account.getPassword(),authorities);
    }
// TODO SCHEDULED ANNOTATIONLIK METHOD XONALARNI RENT VAQTI TUGADIMI YOQMI SHUNI TEKSHIRADI
    @Scheduled(fixedDelay = 45000L)
    void check() {
        List<Orders> orders = orderDAO.getAllOrders();
        for (Orders order : orders) {
            if (LocalTime.now().isAfter(order.getToDate())){
                Room room =roomDAO.findRoomById(order.getRoomId());
                room.setAccountId(null);
                room.setIsRented(false);
                roomDAO.orderChanges(room.getId(), room.getIsRented(),room.getAccountId());
                System.out.println("Order number "+order.getId()+" is expired and room " + order.getRoomId()+ " is free now!");
            }else if (LocalTime.now().isBefore(order.getToDate())){
                System.out.println("Order number "+order.getId()+" not expired yet))");
            }

        }
    }
}
