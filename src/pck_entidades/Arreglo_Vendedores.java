package pck_entidades;
//Declaracion de las librerias adicionales
import java.io.*;
import java.util.ArrayList;

public class Arreglo_Vendedores  implements Serializable
{
//Arreglo de objetos para los registros academicos
private ArrayList <cls_vendedor> a;

//Generacion del contructor
public Arreglo_Vendedores()
{
//Creando el objeto
a = new ArrayList();
}//Fin del constructor

//METEDOS PARA ADMINISTRAR LA INFORMACION DEL ARREGLO
//Adiciona un nuevo registro de datos
public void Agregar(cls_vendedor nuevo)
{
a.add(nuevo);
}

//Obtiene un registro
public cls_vendedor getVendedor(int i)
{
return a.get(i);
}

//Remplaza una informacion de un registro
public void Reemplaza(int i, cls_vendedor Actualizado)
{
a.set(i,Actualizado);
}

//Obteniendo el numero de registros de los alumnos
public int Numero_Vendedores()
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
for(int i=0; i < Numero_Vendedores(); i++)
{
a.remove(0);
}//Fin del for
}

//Busca vendedor por codigo
public int Buscar (String codigo)
{
for(int i=0; i < Numero_Vendedores(); i++)
{
if(codigo.equalsIgnoreCase(getVendedor(i).getCodigo()))
return i;//retornando el indice
}//Fin del for
return -1;//Significa que no encontró ningun valor
}

public int BuscarPorNombre (String nombre)
{
for(int i=0; i < Numero_Vendedores(); i++)
{
if(nombre.equalsIgnoreCase(getVendedor(i).getNombres()))
return i;//retornando el indice
}//Fin del for
return -1;//Significa que no encontró ningun valor
}
    
}//Fin de la clace
