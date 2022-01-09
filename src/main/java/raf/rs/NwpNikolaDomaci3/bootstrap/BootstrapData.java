package raf.rs.NwpNikolaDomaci3.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import raf.rs.NwpNikolaDomaci3.model.*;
import raf.rs.NwpNikolaDomaci3.repositories.ErrorMessRepository;
import raf.rs.NwpNikolaDomaci3.repositories.MachineRepository;
import raf.rs.NwpNikolaDomaci3.repositories.UserRepository;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;


@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MachineRepository machineRepository;

    private final ErrorMessRepository errorMessRepository;


    LocalDate today = LocalDate.now();
    LocalDate year = today.plusYears(1);
    int i;
    Permission permission = new Permission();
    Permission permission2 = new Permission();


    @Autowired
    public BootstrapData(UserRepository userRepository, PasswordEncoder passwordEncoder, MachineRepository machineRepository, ErrorMessRepository errorMessRepository) throws ParseException {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.machineRepository = machineRepository;
        this.errorMessRepository = errorMessRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User();
        user.setEmail("user@gmail.com");
        user.setName("user");
        user.setLastName("user");
        user.setPass(this.passwordEncoder.encode("user"));
        permission.setCanCreate(true);
        permission.setCanUpdate(true);
        permission.setCanDelete(true);
        permission.setCanRead(true);
        permission.setCanStartMachines(true);
        permission.setCanCreateMachines(true);
        permission.setCanDestroyMachines(true);
        permission.setCanRestartMachines(true);
        permission.setCanSearchMachines(true);
        permission.setCanStopMachines(true);
        user.setPermissions(permission);
        userRepository.save(user);

        User user2 = new User();
        user2.setEmail("user2@gmail.com");
        user2.setName("user2");
        user2.setLastName("user2");
        user2.setPass(this.passwordEncoder.encode("user2"));
        permission2.setCanCreate(true);
        permission2.setCanUpdate(true);
        permission2.setCanDelete(true);
        permission2.setCanRead(true);
        permission2.setCanStartMachines(true);
        permission2.setCanCreateMachines(true);
        permission2.setCanDestroyMachines(false);
        permission2.setCanRestartMachines(false);
        permission2.setCanSearchMachines(false);
        permission2.setCanStopMachines(false);
        user2.setPermissions(permission2);
        userRepository.save(user2);


        Machines machines = new Machines();
        machines.setStatus(MachStatus.RUNNING);
        machines.setName("Masina 1");
        machines.setDateFrom(today);
        machines.setDateTo(year);
        machines.setActive(true);
        machines.setUser(user);
        machineRepository.save(machines);

        Machines machines2 = new Machines();
        machines2.setStatus(MachStatus.STOPPED);
        machines2.setName("Masina 2");
        machines2.setDateFrom(today);
        machines2.setDateTo(year);
        machines2.setActive(true);
        machines2.setUser(user2);
        machineRepository.save(machines2);

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setOperation(MachineOperation.STOP);
        errorMessage.setDate(new Date(Calendar.getInstance().getTime().getTime()));
        errorMessage.setMessage("Failed to stop machine");
        errorMessage.setMachines(machines);
        errorMessRepository.save(errorMessage);


        System.out.println("Data loaded!");

    }
}
