package com.example.CsrPrestamo.service;

import com.example.CsrPrestamo.model.Prestamo;
import com.example.CsrPrestamo.repository.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {

 // Se declara el repositorio para poder usar las funciones de la base de datos.
    private final PrestamoRepository prestamoRepository;

    // Constructor para inyectar la dependencia del repositorio.
    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

     /**
     * CREATE (Crear): Guarda un nuevo libro en la base de datos.
     */
    
    public Object crearPrestamo(Prestamo prestamo) {
        if(prestamo.getCodigoLibro().isEmpty()){
            
            return "Error: El código del libro no puede estar vacío";
        
        }
            else if(prestamo.getDiasPrestamo() > 30){
                
                return "Error: El plazo de préstamo excede el límite de 30 días";

            }
                else if(prestamo.getRutUsuario().length() < 8){
                    
                    return "Error: El RUT ingresado es inválido";
                
                }
                    
                    else {

                        prestamo.setFechaInicio(LocalDate.now());
                        return prestamoRepository.save(prestamo);
                    }

        }  
    
    
    public List<Prestamo> obtenerTodos() {
        // El método findAll() trae todos los registros de la tabla
        return prestamoRepository.findAll();
    }

        /**
     * READ (Leer por ID): Busca un libro específico usando su identificador.
     * Devuelve un Optional porque el libro podría no existir.
     */
    public Optional<Prestamo> obtenerPorId(Long id) {
        return prestamoRepository.findById(id);
    }

       /**
     * UPDATE (Actualizar): Reemplaza los datos de un libro existente.
     */
    public Prestamo actualizarLibro(Long id, Prestamo detallesPrestamo) {
        // Primero verificamos si el libro existe en la base de datos
        Optional<Prestamo> prestamoExistente = prestamoRepository.findById(id);
        
        if (prestamoExistente.isPresent()) {
            // Obtenemos el libro real de la base de datos
            Prestamo prestamoAActualizar = prestamoExistente.get();
            // Actualizamos sus datos con los nuevos datos recibidos
            prestamoAActualizar.setCodigoLibro(detallesPrestamo.getCodigoLibro());
            prestamoAActualizar.setRutUsuario(detallesPrestamo.getRutUsuario());
            prestamoAActualizar.setDiasPrestamo(detallesPrestamo.getDiasPrestamo());
            prestamoAActualizar.setFechaInicio(detallesPrestamo.getFechaInicio());
            // Guardamos los cambios
            return prestamoRepository.save(prestamoAActualizar);
        } else {
            // Si no existe, retornamos nulo o podríamos lanzar un error
            return null; 
        }
    }

        /**
     * DELETE (Eliminar): Borra un libro de la base de datos usando su ID.
     */
    public boolean eliminarLibro(Long id) {
        if (prestamoRepository.existsById(id)) {
            prestamoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
