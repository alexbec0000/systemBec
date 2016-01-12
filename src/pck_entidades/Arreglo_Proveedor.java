package pck_entidades;
//Declaracion de las librerias adicionales
import java.io.*;
import java.util.ArrayList;

public class Arreglo_Proveedor implements Serializable
{
    //Arreglo de objetos para los registros academicos
    private ArrayList <cls_proveedor> a;

    //Generacion del contructor
    public Arreglo_Proveedor()
    {
        //Creando el objeto
        a = new ArrayList();
    }//Fin del constructor

    //METEDOS PARA ADMINISTRAR LA INFORMACION DEL ARREGLO
    //Adiciona un nuevo registro de datos
    public void Agregar(cls_proveedor nuevo)
    {
        a.add(nuevo);
    }

    //Obtiene un registro
    public cls_proveedor getProveedor(int i)
    {
        return a.get(i);
    }

    //Remplaza una informacion de un registro
    public void Reemplaza(int i, cls_proveedor Actualizado)
    {
        a.set(i,Actualizado);
    }

    //Obteniendo el numero de registros de los alumnos
    public int Numero_Proveedor()
    {
        return a.size();
    }

    //Elimina un registro del alumno
    public void Elimina(int i)
    {
        a.remove(i);
    }

    //Elimina todo el registro del alumno
    public void Elimina()
    {
        for(int i=0; i < Numero_Proveedor(); i++)
        {
            a.remove(0);
        }//Fin del for
    }

    //Busca proveedor por codigo
    public int Buscar (String codigo)
    {
        for(int i=0; i < Numero_Proveedor(); i++)
        {
            if(codigo.equalsIgnoreCase(getProveedor(i).getCodigo()))
            return i;//retornando el indice
        }//Fin del for
        return -1;//Significa que no encontró ningun valor
    }

    public int BuscarPorNombre (String nombre)
    {
        for(int i=0; i < Numero_Proveedor(); i++)
        {
            if(nombre.equalsIgnoreCase(getProveedor(i).getNombre()))
                return i;//retornando el indice
        }//Fin del for
        return -1;//Significa que no encontró ningun valor
    }

}//Fin de la clace

