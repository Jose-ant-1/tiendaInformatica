package stream;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.persistence.EntityManager;
import org.hibernate.persister.collection.mutation.UpdateRowsCoordinator;
import org.iesbelen.tiendainformatica.dao.FabricanteDAO;
import org.iesbelen.tiendainformatica.dao.ProductoDAO;
import org.iesbelen.tiendainformatica.entity.Fabricante;
import org.iesbelen.tiendainformatica.dao.FabricanteDAOImpl;
import org.iesbelen.tiendainformatica.entity.Producto;
import org.iesbelen.tiendainformatica.dao.ProductoDAOImpl;
import org.iesbelen.tiendainformatica.util.JPAUtil;
import org.junit.jupiter.api.*;

import static java.util.Comparator.comparing;
import static org.junit.jupiter.api.Assertions.assertEquals;


class TiendaTest {

    // Obtenemos un EntityManager de nuestra clase de utilidad JPA
    private static EntityManager em = JPAUtil.getEntityManager();

    // Los DAOs son estáticos para poder usarlos en @BeforeAll
    private static FabricanteDAO fabricantesDAO = new FabricanteDAOImpl(em);
    private static ProductoDAO productosDAO = new ProductoDAOImpl(em);

    @BeforeAll
    static void setUpAll() {
        // ✅ ESTO SE EJECUTA UNA SOLA VEZ ANTES DE TODOS LOS TESTS
        System.out.println("Inicializando datos de la base de datos...");
        ((FabricanteDAOImpl) fabricantesDAO).beginTransaction();
        try {
            // Creación de fabricantes y productos
            Fabricante asus = new Fabricante("Asus");
            fabricantesDAO.create(asus);
            Producto producto1 = new Producto(asus, "Monitor 27 LED Full HD", 199.25);
            productosDAO.create(producto1);
            asus.addProducto(producto1);
            Producto producto2 = new Producto(asus, "Monitor 24 LED Full HD", 119.99);
            productosDAO.create(producto2);
            asus.addProducto(producto2);

            Fabricante lenovo = new Fabricante("Lenovo");
            fabricantesDAO.create(lenovo);
            producto1 = new Producto(lenovo, "Portátil IdeaPad 320", 359.65);
            productosDAO.create(producto1);
            lenovo.addProducto(producto1);
            producto2 = new Producto(lenovo, "Portátil Yoga 520", 452.79);
            productosDAO.create(producto2);
            lenovo.addProducto(producto2);

            Fabricante hp = new Fabricante("Hewlett-Packard");
            fabricantesDAO.create(hp);
            producto1 = new Producto(hp, "Impresora HP Deskjet 3720", 59.99);
            productosDAO.create(producto1);
            hp.addProducto(producto1);
            producto2 = new Producto(hp, "Impresora HP Laserjet Pro M26nw", 111.86);
            productosDAO.create(producto2);
            hp.addProducto(producto2);

            Fabricante samsung = new Fabricante("Samsung");
            fabricantesDAO.create(samsung);
            producto1 = new Producto(samsung, "Disco SSD 1 TB", 72.99);
            productosDAO.create(producto1);
            samsung.addProducto(producto1);

            Fabricante seagate = new Fabricante("Seagate");
            fabricantesDAO.create(seagate);
            producto1 = new Producto(seagate, "Disco duro SATA3 1TB", 38.49);
            productosDAO.create(producto1);
            seagate.addProducto(producto1);

            Fabricante crucial = new Fabricante("Crucial");
            fabricantesDAO.create(crucial);
            producto1 = new Producto(crucial, "GeForce GTX 1080 Xtreme", 611.55);
            productosDAO.create(producto1);
            crucial.addProducto(producto1);
            producto2 = new Producto(crucial, "Memoria RAM DDR4 8GB", 24.19);
            productosDAO.create(producto2);
            crucial.addProducto(producto2);

            Fabricante gigabyte = new Fabricante("Gigabyte");
            fabricantesDAO.create(gigabyte);
            producto1 = new Producto(gigabyte, "GeForce GTX 1050Ti", 319.19);
            productosDAO.create(producto1);
            gigabyte.addProducto(producto1);

            fabricantesDAO.create(new Fabricante("Huawei"));
            fabricantesDAO.create(new Fabricante("Xiaomi"));

            ((FabricanteDAOImpl) fabricantesDAO).commitTransaction();
        } catch (Exception e) {
            ((FabricanteDAOImpl) fabricantesDAO).rollbackTransaction();
            throw e;
        }
    }

    @BeforeEach
    void setUp() {
        // ✅ Se ejecuta antes de CADA test
        // Ideal para iniciar una transacción que se revertirá después
        ((FabricanteDAOImpl) fabricantesDAO).beginTransaction();
        // Inicia la transacción UNA SOLA VEZ
        // No importa a través de qué DAO lo hagas, ya que ambos comparten la transacción.
        // ((ProductoDAOImpl) productosDAO).beginTransaction();
    }

    @AfterEach
    void tearDown() {
        // ✅ Se ejecuta después de CADA test
        // Revertimos la transacción para que cada test sea independiente
        ((FabricanteDAOImpl) fabricantesDAO).rollbackTransaction();
    }

    @AfterAll
    static void tearDownAll() {
        // ✅ Se ejecuta UNA SOLA VEZ al final de todos los tests
        // Ideal para limpiar la base de datos si es necesario.
        System.out.println("Limpiando datos...");
        if (em != null) {
            em.close();
            System.out.println("EntityManager cerrado.");
        }
    }

    @Test
    void testAllFabricante() {
        List<Fabricante> listFab = fabricantesDAO.findAll();
        assertEquals(9, listFab.size());
    }

    @Test
    void testAllProducto() {

        List<Producto> listProd = productosDAO.findAll();
        assertEquals(11, listProd.size());

    }

    @Test
    void testSkeletonFrabricante() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }

    @Test
    void testSkeletonProducto() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }

    @Test
    void test1() {
//1. Lista los nombres y los precios de todos los productos de la tabla producto
        List<Producto> listProd = productosDAO.findAll();
/*
       List<String> listaNombrePrecio = listProd.stream().
               map(prod -> prod.getNombre() + " - " + prod.getPrecio()).toList();

       listaNombrePrecio.forEach(System.out::println);
*/
       List<String> lista = listProd.stream().map(p -> p.getNombre() + " - " + p.getPrecio()).toList();
       lista.forEach(System.out::println);
    }


    @Test
    void test2() {
        //2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares.
        List<Producto> listProd = productosDAO.findAll();
/*
        List<String> listaEruoADolar = listProd.stream().
                map(prod -> prod.getNombre() +
                        " - Fab= " + prod.getFabricante().getIdFabricante() +
                        " - IdProduc= " + prod.getIdProducto() +
                        " - PrecioDolar= " + new BigDecimal(prod.getPrecio() * 1.17))
                .toList();
        listaEruoADolar.forEach(System.out::println);
*/
        List<String> lista = listProd.stream().map(p -> p.getIdProducto() + " - " +  p.getPrecio() * 1.17).toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test3() {
        //3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.

        List<Producto> listProd = productosDAO.findAll();
        /*
        List<String> listaNombrePrecioMayus = listProd.stream()
                .map(prod -> prod.getNombre().toUpperCase() + " - " + prod.getPrecio())
                .toList();
        listaNombrePrecioMayus.forEach(System.out::println);
        */
        List<String> lista = listProd.stream().map(p -> p.getNombre().toUpperCase() + " - " + p.getPrecio()).toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test4() {
//4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del
//nombre del fabricante.
        List<Fabricante> listFab = fabricantesDAO.findAll();
        /*
        List<String> listaNomFab2PrimerCaracter = listFab.stream()
            .map(fab -> fab.getNombre().substring(0,2).toUpperCase())
            .toList();

        listaNomFab2PrimerCaracter.forEach(System.out::println);
        */
        List<String> lista = listFab.stream().map( f -> f.getNombre().toUpperCase().substring(0,2)).toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test5() {
//5. Lista el código de los fabricantes que tienen productos.
        List<Fabricante> listFab = fabricantesDAO.findAll();
/*
        List<Integer> listCodFabConProductos = listFab.stream().
                filter(fab -> (fab.getProductos() != null) && (!fab.getProductos().isEmpty()))
            .map(Fabricante::getIdFabricante).toList();
        listCodFabConProductos.forEach(System.out::println);
*/
        Stream<Integer> lista = listFab.stream().filter(f -> f.getProductos() != null && !f.getProductos().isEmpty()).map(Fabricante::getIdFabricante);
        lista.forEach(System.out::println);
    }


    @Test
    void test6() {
//6. Lista los nombres de los fabricantes ordenados de forma descendente.
        List<Fabricante> listFab = fabricantesDAO.findAll();
        /*
        List<String> listaOrden = listFab.stream().sorted(comparing(Fabricante::getNombre).reversed())
                .map(Fabricante::getNombre).toList();

        listaOrden.forEach(System.out::println);
        */
        List<String> lista = listFab.stream().sorted(comparing(Fabricante::getNombre).reversed()).map(Fabricante::getNombre).toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test7() {
// 7. Lista los nombres de los productosDAOImpl ordenados en primer lugar por el nombre de forma ascendente y
// en segundo lugar por el precio de forma descendente.
        List<Producto> listProd = productosDAO.findAll();
/*
        List<String> listNomOrdAscYDesc = listProd.stream().
                sorted(comparing(Producto::getNombre).
                        thenComparing(comparing(Producto::getPrecio).
                                reversed())).
                map(Producto::getNombre).toList();

            listNomOrdAscYDesc.forEach(System.out::println);
*/
    List<String> lista = listProd.stream().
            sorted(comparing(Producto::getNombre).
                    thenComparing(Producto::getPrecio).reversed()).
            map(p -> p.getNombre() + " - " + p.getPrecio())
            .toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test8() {
//8. Devuelve una lista con los 5 primeros fabricantes.

        List<Fabricante> listFab = fabricantesDAO.findAll();
/*
        List<String> lista5PrimerosFab = listFab.stream().
                limit(5).
                map(fab -> fab.getIdFabricante() + " - " + fab.getNombre()).toList();

        lista5PrimerosFab.forEach(System.out::println);
*/
        List<String> lista = listFab.stream().limit(5).map(Fabricante::getNombre).toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test9() {
// 9. Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también
// se debe incluir en la respuesta.

        List<Fabricante> listFab = fabricantesDAO.findAll();
/*
        List<String> fabricantes2 = listFab.stream().skip(3).limit(2).
                map(fab -> fab.getIdFabricante() + " - " + fab.getNombre()).toList();

        fabricantes2.forEach(System.out::println);
 */
        List<String> lista = listFab.stream().skip(3).limit(2).map(Fabricante::getNombre).toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test10() {
// 10. Lista el nombre y el precio del producto más barato
        List<Producto> listProd = productosDAO.findAll();
/*
        Producto productoMasBarato = listProd.stream().min(comparing(Producto::getPrecio)).orElse(null);
        if(productoMasBarato != null) {
            System.out.println(productoMasBarato.getNombre() + " - " + productoMasBarato.getPrecio());
        }
*/
        Producto producto = listProd.stream().min(comparing(Producto::getPrecio)).orElse(null);
        if (producto != null) {
            System.out.println("El producto más barato es " + producto.getNombre() + " - " + producto.getPrecio());
        }
    }

    @Test
    void test11() {
// 11. Lista el nombre y el precio del producto más caro
        List<Producto> listProd = productosDAO.findAll();
/*
        List<String> prodCaro = listProd.stream().max(comparing(Producto::getPrecio)).
                map(prod -> prod.getNombre() + " - " + prod.getPrecio()).
                stream().toList();

        prodCaro.forEach(System.out::println);
 */
        Producto producto = listProd.stream().max(comparing(Producto::getPrecio)).orElse(null);
        if (producto != null) {
            System.out.println("El producto más caro es" +  producto.getNombre() + " - " + producto.getPrecio());
        }

    }


    @Test
    void test12() {
//12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
        List<Producto> listProd = productosDAO.findAll();
/*
        List<String> listCod2 = listProd.stream().
                filter(prod -> prod.getFabricante().getIdFabricante().equals(2)).
                map(producto -> producto.getNombre() + " - " + producto.getFabricante().getIdFabricante()).
                toList();
        listCod2.forEach(System.out::println);

 */
        List<String> lista = listProd.stream().
                filter(p -> p.getFabricante().getIdFabricante().equals(2)).
                map(p -> p.getNombre() + " - " + p.getFabricante().getIdFabricante()).
                toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test13() {
//13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
        List<Producto> listProd = productosDAO.findAll();
/*
        List<String> prodPrecioMenos120 = listProd.stream().
                filter(prod -> prod.getPrecio() <= 120).
                map(prod -> prod.getNombre() + " - " + prod.getPrecio()).
                toList();
        prodPrecioMenos120.forEach(System.out::println);

 */
        List<String> lista = listProd.stream().
                filter(p -> p.getPrecio() <= 120).
                map(p -> p.getNombre() + " - " + p.getPrecio()).toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test14() {
//14. Lista los productos que tienen un precio mayor o igual a 400€.
        List<Producto> listProd = productosDAO.findAll();
/*
        List<String> listPrecioMayor400 = listProd.stream().filter(prod -> prod.getPrecio() >= 400).
        map(prod -> prod.getNombre() + " - " + prod.getPrecio()).toList();

        listPrecioMayor400.forEach(System.out::println);

 */
        List<String> lista = listProd.stream().
                filter(p -> p.getPrecio() >= 400).
                map(Producto::getNombre).toList();
    lista.forEach(System.out::println);
    }


    @Test
    void test15() {
// 15. Lista todos los productos que tengan un precio entre 80€ y 300€.
        List<Producto> listProd = productosDAO.findAll();
/*
        List<String> listprecioEntre80Y300 = listProd.stream().
        filter(prod -> prod.getPrecio() >= 80 && prod.getPrecio() <= 300).
        map(prod -> prod.getNombre() + " - " + prod.getPrecio()).toList();

        listprecioEntre80Y300.forEach(System.out::println);

 */
        List<String> lista = listProd.stream().
                filter(p -> p.getPrecio() >= 80 && p.getPrecio() <= 300).
                map(p -> p.getPrecio() + " - " + p.getNombre()).toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test16() {
//16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
        List<Producto> listProd = productosDAO.findAll();
/*
        List<String> precioMayor200Codigo6 = listProd.stream().
                filter(prod -> prod.getPrecio() > 200 && prod.getFabricante().getIdFabricante() == 6).
                map(prod -> prod.getNombre() + " - " + prod.getPrecio()).toList();

        precioMayor200Codigo6.forEach(System.out::println);
*/
        List<String> lista = listProd.stream().
                filter(p -> p.getPrecio() > 200 && p.getFabricante().getIdFabricante().equals(6)).
                map(p -> p.getNombre() + " - " + p.getPrecio()).toList();
        lista.forEach(System.out::println);
    }


    @Test
    void test17() {
// 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes
// para filtrar.
        List<Producto> listProd = productosDAO.findAll();
        Set<Integer> codigoFabricanteValido = Set.of(1, 3, 5);
        List<String> prodFab135UsandoSetCodigo = listProd.stream().
                filter(prod -> codigoFabricanteValido.contains(prod.getFabricante().getIdFabricante())).
                map(p -> p.getNombre() + " - " + p.getFabricante().getIdFabricante()).toList();

        prodFab135UsandoSetCodigo.forEach(System.out::println);
    }


    @Test
    void test18() {
// 18. Lista el nombre y el precio de los productos en céntimos.
        List<Producto> listProd = productosDAO.findAll();

        List<String> prodEnCentimos = listProd.stream().
                map(p -> p.getNombre() + " - " + p.getPrecio() * 100).
                toList();
        prodEnCentimos.forEach(System.out::println);
    }



    @Test
    void test19() {
//19. Lista los nombres de los fabricante cuyo nombre empiece por la letra S
        List<Fabricante> listFab = fabricantesDAO.findAll();

        List<String> fabEmpiezaPorS = listFab.stream().
                filter(f -> f.getNombre().toUpperCase().startsWith("S"))
                .map(Fabricante::getNombre).toList();
        fabEmpiezaPorS.forEach(System.out::println);
    }


    @Test
    void test20() {
// 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.

        List<Producto> listProd = productosDAO.findAll();

        List<String> portatiles = listProd.stream().
                filter(prod -> prod.getNombre().contains("Portátil")).
                map(Producto::getNombre).
                toList();

        portatiles.forEach(System.out::println);
    }


    @Test
    void test21() {
//21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y
//tienen un precio inferior a 150 €.
        List<Producto> listProd = productosDAO.findAll();

        List<String> portatilesMenoresDe150 = listProd.stream().
                filter(prod -> prod.getNombre().contains("Monitor") && prod.getPrecio() < 150).
                map(p -> p.getNombre() + " - " + p.getPrecio() ).
                toList();

        portatilesMenoresDe150.forEach(System.out::println);

    }


    @Test
    void test22() {
//22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€.
//Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por
//el nombre (en orden ascendente).
        List<Producto> listProd = productosDAO.findAll();

        List<String> prodMayorIgual180 = listProd.stream().
                filter(p -> p.getPrecio() >= 180).
                sorted(comparing(Producto::getPrecio).reversed().
                        thenComparing(comparing(Producto::getNombre))).
                map(prod -> prod.getNombre() + " - " + prod.getPrecio()).
                toList();
        prodMayorIgual180.forEach(System.out::println);
    }


    @Test
    void test23() {
//23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de
//la base de datos. Ordene el resultado por el nombre del fabricante, por orden alfabético.
        List<Producto> listProd = productosDAO.findAll();

        List<String> listaProdPrecNomOrdenado = listProd.stream()
                .sorted(comparing(prod -> prod.getFabricante().getNombre()))
                .map(prod -> prod.getPrecio() + " - " +
                        prod.getNombre() + " - " +
                        prod.getFabricante().getNombre())
                .toList();
        listaProdPrecNomOrdenado.forEach(System.out::println);
    }


    @Test
    void test24() {
//24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
        List<Producto> listProd = productosDAO.findAll();

        List<String> prodMasCaro = listProd.stream().
                max(comparing(Producto::getPrecio)).
                stream().
                map(prod -> prod.getNombre() +
                        " - " + prod.getPrecio() +
                        " - " + prod.getFabricante().getNombre()).toList();
        prodMasCaro.forEach(System.out::println);
    }


    @Test
    void test25() {
//25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
        List<Producto> listProd = productosDAO.findAll();

        List<String> prodFabCrucialMayor200 = listProd.stream().
                filter(prod -> "Crucial".equals(prod.getFabricante().getNombre())
                    && prod.getPrecio() > 200).
                map(prod -> prod.getNombre() + " - " + prod.getPrecio()).
                toList();
        prodFabCrucialMayor200.forEach(System.out::println);
    }


    @Test
    void test26() {
//26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
        List<Producto> listProd = productosDAO.findAll();
        Set<String> fabricantesAFitrar = Set.of("Asus", "Hewlett-Packard", "Seagate");
        List<String> productosConFiltro = listProd.stream().
                filter(prod -> fabricantesAFitrar.contains(prod.getFabricante().getNombre())).
                map(prod -> prod.getNombre() + " - " + prod.getFabricante().getNombre()).
                toList();
        productosConFiltro.forEach(System.out::println);
    }


    @Test
    void test27() {
//27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos
//que tengan un precio mayor o igual a 180€. Ordene el resultado en primer lugar por el precio (en orden descendente)
//y en segundo lugar por el nombre.
//
//La salida debe quedar tabulada como sigue:
//<p>
//Producto                Precio             Fabricante
//-----------------------------------------------------
//GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
//Portátil Yoga 520      |452.79            |Lenovo
//Portátil Ideapd 320    |359.64000000000004|Lenovo
//Monitor 27 LED Full HD |199.25190000000003|Asus
        List<Producto> listProd = productosDAO.findAll();
//27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos
//que tengan un precio mayor o igual a 180€. Ordene el resultado en primer lugar por el precio (en orden descendente)
//y en segundo lugar por el nombre.

        List<Producto> productosFiltrados = listProd.stream()
                .filter(p -> p.getPrecio() >= 180)
                .sorted(comparing(Producto::getPrecio).reversed().thenComparing(Producto::getNombre))
                .toList();
        System.out.println("Producto                Precio             Fabricante");
        System.out.println("-----------------------------------------------------");
        productosFiltrados.forEach(p ->{
                System.out.printf("%-23s|%.2f|%s%n", p.getNombre(), p.getPrecio(),p.getFabricante().getNombre());
        });
    }


    @Test
    void test28() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        listFab.forEach(fab -> {
                    System.out.println("Fabricante: " + fab.getNombre());
                    System.out.println("\t Productos");
                    if ((fab.getProductos() != null) && (!fab.getProductos().isEmpty()))
                        fab.getProductos().forEach(p -> System.out.println(p.getNombre()));
                });

    }


    @Test
    void test29() {
//29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
        List<Fabricante> listFab = fabricantesDAO.findAll();

        List<String> fabSinProd = listFab.stream().
                filter(fabricante -> fabricante.getProductos() != null && fabricante.getProductos().isEmpty()).
                map(Fabricante::getNombre).
                toList();
        fabSinProd.forEach(System.out::println);
    }


    @Test
    void test30() {
//30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
        List<Producto> listProd = productosDAO.findAll();

        long cantidadProducost = listProd.stream().count();
        System.out.println("Cantidad de productos: " + cantidadProducost);
    }



    @Test
    void test31() {
//31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
        List<Producto> listProd = productosDAO.findAll();

        long fabricantes = listProd.stream().map(Producto::getFabricante).distinct().count();
        System.out.println("Fabricantes: " + fabricantes);
    }


    @Test
    void test32() {
//32. Calcula la media del precio de todos los productos
        List<Producto> listProd = productosDAO.findAll();
        double precioTodosProductos = listProd.stream().mapToDouble(Producto::getPrecio).average().orElse(0);

        System.out.println("Media de precios: " + precioTodosProductos);
    }


    @Test
    void test33() {
//33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
        List<Producto> listProd = productosDAO.findAll();

        double precioMasBajo = listProd.stream().mapToDouble(Producto::getPrecio).min().orElse(0);

        System.out.println("Precio más bajo: " + precioMasBajo);
    }


    @Test
    void test34() {
//34. Calcula la suma de los precios de todos los productos.
        List<Producto> listProd = productosDAO.findAll();

        double sumaPrecios = listProd.stream().mapToDouble(Producto::getPrecio).sum();

        System.out.println("Suma de precios: " + sumaPrecios);

    }


    @Test
    void test35() {
//35. Calcula el número de productos que tiene el fabricante Asus.
        List<Producto> listProd = productosDAO.findAll();

        long CantidadProdAsus = listProd.stream().filter(p -> "Asus".equals(p.getFabricante().getNombre())).count();

        System.out.println("Cantidad de productos de Asus: " + CantidadProdAsus);

    }


    @Test
    void test36() {
//36. Calcula la media del precio de todos los productos del fabricante Asus.
        List<Producto> listProd = productosDAO.findAll();

        double mediaPrecios = listProd.stream().
                filter(p -> "Asus".equals(p.getFabricante().getNombre())).
                mapToDouble(Producto::getPrecio).average().orElse(0);

        System.out.println("Media de precios de Asus: " + mediaPrecios);

    }


    @Test
    void test37() {
//37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene
//el fabricante Crucial. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
        List<Producto> listProd = productosDAO.findAll();

        // Acumulador: [0] = count, [1] = sum, [2] = min, [3] = max
        Double[] stats = listProd.stream()
                .filter(p -> p.getFabricante().getNombre().equals("Crucial"))
                .map(Producto::getPrecio)
                .reduce(new Double[]{0.0, 0.0, Double.MAX_VALUE, Double.MIN_VALUE},
                        (acumulador, precio) -> {
                            acumulador[0]++; // count
                            acumulador[1] += precio; // sum
                            acumulador[2] = Math.min(acumulador[2], precio); // min
                            acumulador[3] = Math.max(acumulador[3], precio); // max
                            return acumulador;
                        },
                        (acc1, acc2) -> acc1 // Combiner no es necesario en secuencial
                );

        double count = stats[0];
        double sum = stats[1];
        double min = stats[2];
        double max = stats[3];
        double avg = (count > 0) ? sum / count : 0.0;

        System.out.printf("Stats para Crucial: Total=%d, Mín=%.2f, Máx=%.2f, Media=%.2f%n",
                (int) count, min, max, avg);

    }


    @Test
    void test38() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test39() {
//39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes.
//El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Deben aparecer los fabricantes
//que no tienen productos.
        List<Fabricante> listFab = fabricantesDAO.findAll();

        listFab.forEach(f -> {
            if (f.getProductos() != null && !f.getProductos().isEmpty()) {
                DoubleSummaryStatistics stats = f.getProductos().stream().mapToDouble(Producto::getPrecio).summaryStatistics();
                System.out.printf("fabricante: %s Min: %.2f, Max: %.2f Media: %.2f %n",f.getNombre(), stats.getMin(), stats.getMax(), stats.getAverage());
            }
        });


    }


    @Test
    void test40() {
//40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes
//que tienen un precio medio superior a 200€. No es necesario mostrar el nombre del fabricante,
//con el código del fabricante es suficiente.
        List<Producto> listProd = productosDAO.findAll();
    //REHACER
        Map<Fabricante, DoubleSummaryStatistics> stasFabricante = listProd.stream().
                collect(Collectors.groupingBy(
                        Producto::getFabricante,
                        Collectors.summarizingDouble(Producto::getPrecio)));

        stasFabricante.entrySet().stream().
                filter(entry -> entry.getValue().getMin() > 200).
                peek(entry -> {
                   System.out.println("Fabricante ID: %d%n" + entry.getKey().getIdFabricante());
                   DoubleSummaryStatistics stats = entry.getValue();
                   System.out.printf("Total %d, Min: %.2f, Max %.2f, Media: %.2f%n",stats.getCount(), stats.getMin(), stats.getMax(), stats.getAverage());
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    }


    @Test
    void test41() {
//41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
        List<Fabricante> listFab = fabricantesDAO.findAll();

        List<String> listaFabFiltrado = listFab.stream().
                filter(f -> f.getProductos() != null && 2 == f.getProductos().size()).
                map(Fabricante::getNombre).toList();

        listaFabFiltrado.forEach(System.out::println);
    }


    @Test
    void test42() {
//42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno
//con un precio superior o igual a 220 €. Ordenado de mayor a menor número de productos.
        List<Producto> listprod = productosDAO.findAll();

        Map<String,Long> fabYprods = listprod.stream().
                filter(p -> p.getPrecio() >= 220).
                collect(Collectors.groupingBy(p -> p.getFabricante().getNombre(), Collectors.counting()));

        fabYprods.entrySet().stream().
                sorted(Map.Entry.<String,Long>comparingByValue().reversed()).
                forEach(entry -> System.out.printf("Fabricante: %s, #Productos >= 220€: %d%n", entry.getKey(), entry.getValue() ));
    }


    @Test
    void test43() {
//43. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos
// es superior a 500 €
        List<Fabricante> listFab = fabricantesDAO.findAll();

        List<String> nombres = listFab.stream()
                .filter(f -> f.getProductos() != null && f.getProductos().stream().mapToDouble(Producto::getPrecio).sum() > 500)
                .map(Fabricante::getNombre)
                .toList();

        System.out.println("Fabricantes con suma de precios > 500€: " + nombres);

    }


    @Test
    void test44() {
//44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos
//es superior a 600 €. Ordenado de menor a mayor por cuantía de precio de los productos.
        List<Fabricante> listFab = fabricantesDAO.findAll();

        List<String> nombres = listFab.stream()
                .filter(f -> f.getProductos() != null && !f.getProductos().isEmpty())
                .map(f -> new AbstractMap.SimpleEntry<>(
                        f.getNombre(),
                        f.getProductos().stream().mapToDouble(Producto::getPrecio).sum()
                ))
                .filter(entry -> entry.getValue() > 600)
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toList();
    //REHACER
        System.out.println("Fabricantes con suma de precios > 600€ (ordenado): " + nombres);

    }


    @Test
    void test45() {
//45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante.
//El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante.
//El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
        List<Fabricante> listFab = fabricantesDAO.findAll();

        List<String> resultado = listFab.stream()
                .filter(f -> f.getProductos() != null && !f.getProductos().isEmpty())
                .sorted(comparing(Fabricante::getNombre))
                .map(f -> {
                    Producto masCaro = f.getProductos().stream()
                            .max(comparing(Producto::getPrecio))
                            .get();
                    return String.format("%s - %.2f€ - %s", masCaro.getNombre(), masCaro.getPrecio(), f.getNombre());
                })
                .toList();

        resultado.forEach(System.out::println);

    }
}