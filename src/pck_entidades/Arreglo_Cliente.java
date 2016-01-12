package pck_entidades;
//Declaracion de las librerias adicionales
import java.io.*;
import java.util.ArrayList;

public class Arreglo_Cliente implements Serializable
{
    //Arreglo de objetos para los registros academicos
    private ArrayList <cls_cliente> a;

    //Generacion del contructor
    public Arreglo_Cliente()
    {
    //Creando el objeto
        a = new ArrayList();
    }//Fin del constructor

    //METEDOS PARA ADMINISTRAR LA INFORMACION DEL ARREGLO
    //Adiciona un nuevo registro de datos
    public void Agregar(cls_cliente nuevo)
    {
        a.add(nuevo);
    }

    //Obtiene un registro
    public cls_cliente getCliente(int i)
    {
        return a.get(i);
    }

    //Remplaza una informacion de un registro
    public void Reemplaza(int i, cls_cliente Actualizado)
    {
        a.set(i,Actualizado);
    }

    //Obteniendo el numero de registros de los alumnos
    public int Numero_Clientes()
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
        for(int i=0; i < Numero_Clientes(); i++)
        {
            a.remove(0);
        }//Fin del for
    }

    //Busca alumno por codigo
    public int Buscar (String codigo)
    {
        for(int i=0; i < Numero_Clientes(); i++)
        {
            if(codigo.equalsIgnoreCase(getCliente(i).getCodigo()))
            return i;//retornando el indice
        }//Fin del for
        return -1;//Significa que no encontró ningun valor
    }

    public int BuscarPorNombre (String nombre)
    {
        for(int i=0; i < Numero_Clientes(); i++)
        {
            if(nombre.equalsIgnoreCase(getCliente(i).getNombre()))
            return i;//retornando el indice
        }//Fin del for
        return -1;//Significa que no encontró ningun valor
    }

}//Fin de la clace
