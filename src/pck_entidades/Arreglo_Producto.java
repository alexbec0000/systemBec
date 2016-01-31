package pck_entidades;
//Declaracion de las librerias adicionales

import java.io.*;
import java.util.ArrayList;

public class Arreglo_Producto implements Serializable {
//Arreglo de objetos 

    private ArrayList<cls_producto> a;

//Generacion del contructor
    public Arreglo_Producto() {
//Creando el objeto
        a = new ArrayList();
    }//Fin del constructor

//METEDOS PARA ADMINISTRAR LA INFORMACION DEL ARREGLO
//Adiciona un nuevo registro de datos
    public void Agregar(cls_producto nuevo) {
        a.add(nuevo);
    }

//Obtiene un producto
    public cls_producto getProducto(int i) {
        return a.get(i);
    }

//Remplaza una informacion de un registro
    public void Reemplaza(int i, cls_producto Actualizado) {
        a.set(i, Actualizado);
    }

//Obteniendo el numero de registros de los alumnos
    public int Numero_Producto() {
        return a.size();
    }

//Elimina un registro del alumno
    public void Elimina(int i) {
        a.remove(i);
    }

//Elimina todo el registro del alumno
    public void Elimina() {
        for (int i = 0; i < Numero_Producto(); i++) {
            a.remove(0);
        }//Fin del for
    }

//Busca proveedor por codigo
    public int Buscar(String codigo) {
        for (int i = 0; i < Numero_Producto(); i++) {
            if (codigo.equalsIgnoreCase(getProducto(i).getCodigo_art())) {
                return i;//retornando el indice
            }
        }//Fin del for
        return -1;//Significa que no encontró ningun valor
    }

    public int BuscarPorNombre(String nombre) {
        for (int i = 0; i < Numero_Producto(); i++) {
            if (nombre.equalsIgnoreCase(getProducto(i).getNombre())) {
                return i;//retornando el indice
            }
        }//Fin del for
        return -1;//Significa que no encontró ningun valor
    }

}//Fin d la clace
