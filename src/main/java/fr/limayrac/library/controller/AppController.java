package fr.limayrac.library.controller;

import fr.limayrac.library.model.*;
import fr.limayrac.library.repository.EmpruntsRepository;
import fr.limayrac.library.repository.LivreRepository;
import fr.limayrac.library.repository.UserRepository;
import fr.limayrac.library.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmpruntsRepository empruntsRepo;

    @Autowired
    private LivreRepository livreRepo;

    @GetMapping("")
    public String index(){
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new Users());

        return "form/signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(Users user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "form/register_success";
    }

    @GetMapping("/livres")
    public String listLivres (Model model){
        List<LivresPojo> booksList = livreRepo.findAllByEtatAndTitle();
        model.addAttribute("booksList", booksList);

        return "/livre/list";
    }

    @GetMapping("/livres/ListeLivre")
    public String listLivresByTitle (@RequestParam(value = "titre", required = false) String titre, @RequestParam(value = "etat", required = false) String etat , Model model){
        List<Livres> booksList = livreRepo.findByEtatAndTitle(titre, etat);
        model.addAttribute("booksList", booksList);

        return "/livre/listDetailsLivres";
    }

    @GetMapping("/livres/listLivreEmp")
    public String listLivresByTitle (Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Livres> booksList = livreRepo.findLivreEmpByEtatAndTitle(((CustomUserDetails)principal).getUser().getId());
        model.addAttribute("booksList", booksList);

        return "/livre/ListEmprunt";
    }

    @GetMapping("/livres/returnBook")
    public RedirectView returnBook (@RequestParam(value = "idLivre", required = true) Long idLivre, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Emprunts emprunt = empruntsRepo.findIdUserAndIdLivre(((CustomUserDetails)principal).getUser().getId(), idLivre);
        emprunt.setRendu(true);
        empruntsRepo.save(emprunt);

        return new RedirectView("/livres");
    }

    @GetMapping("/livres/Livre/Details/{id}")
    public RedirectView DetailsLivre (Model model, @PathVariable Long id){
        Livres book = livreRepo.findById(id).orElse(null);
        model.addAttribute("livre", book);

        return new RedirectView("/livres");
    }

    @GetMapping("/livres/Livre/Emprunter/{id}")
    public String EmprunterLivre (Model model, @PathVariable Long id){
        Livres book = livreRepo.findById(id).orElse(null);
        EmpruntsPojo emprunt = new EmpruntsPojo();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", ((CustomUserDetails)principal).getUser() );
        model.addAttribute("livre", book);
        model.addAttribute("emprunt", emprunt);

        return "/livre/EmprunterLivre";
    }

    @PostMapping ("/livres/Livre/Emprunter/{id}/Add")
    public RedirectView AddEmprunt (EmpruntsPojo empruntsPojo) throws ParseException {
        Emprunts emprunts = new Emprunts();
        Calendar c = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(formatter.parse(empruntsPojo.getDateEmprunt()));
        c.add(Calendar.DAY_OF_MONTH, empruntsPojo.getnbJours());
        emprunts.setDateEmprunt(empruntsPojo.getDateEmprunt());
        emprunts.setDateRendu(formatter.format(c.getTime()));
        emprunts.setLivre(livreRepo.getById(empruntsPojo.getIdLivre()));
        emprunts.setUser(userRepo.getById(empruntsPojo.getIdUser()));
        emprunts.setRendu(false);

        empruntsRepo.save(emprunts);

        return new RedirectView("/livres");
    }

    @GetMapping("/livres/Livre/Supprimer/{id}")
    public RedirectView DeleteLivre (@PathVariable Long id){
        livreRepo.deleteById(id);

        return new RedirectView("/livres");
    }

    @GetMapping("/livres/newLivre")
    public String AddLivre (Model model){
        model.addAttribute("livre", new Livres());

        return "/livre/addLivre";
    }

    @PostMapping("/livres/process_livre_register")
    public RedirectView AddLivre (Livres livre){
        livreRepo.save(livre);

        return new RedirectView("/livres");
    }
}
