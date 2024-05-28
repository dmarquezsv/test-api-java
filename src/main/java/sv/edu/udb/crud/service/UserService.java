package sv.edu.udb.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sv.edu.udb.crud.model.User;
import sv.edu.udb.crud.repository.UserRepository;

import java.util.HashMap;
import java.util.List;

import java.util.Optional;

@Service
public class UserService {
    HashMap<String , Object> data;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
    }

    //Listar los usuarios
    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public ResponseEntity<Object> newUser(User user) {
        //Busqueda para encontrar un nombre de la base de
        Optional<User> res = userRepository.findUserByName(user.getName());
        data = new HashMap<>();

        /*/Verificar si existe el nombre
        if(res.isPresent() && user.getId() > 0 ){
            data.put("error", true);
            data.put("message","Ya existe un usuario con ese nombre");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
        /*/

        if(res.isPresent()){
            data.put("error", true);
            data.put("message","Ya existe un usuario con ese nombre");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        data.put("message","Se guardado con éxito");
        //Actualizar Usuario.
       /* if(user.getId() >0){
            data.put("message","Se actualizó con éxito");
        }
        */
        userRepository.save(user);
        data.put("data",user);
        return  new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );

    }

    public ResponseEntity<Object> deleteUser(int id){
        data = new HashMap<>();
        boolean status = this.userRepository.existsById(id);

       if(!status){
           data.put("error", true);
           data.put("message","No existe un usuario con ese ID");
           return new ResponseEntity<>(
                   data,
                   HttpStatus.CONFLICT
           );
       }
        userRepository.deleteById(id); // Usuario Eliminado por ID
        data.put("message","Usuario eliminado");
        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }
}
