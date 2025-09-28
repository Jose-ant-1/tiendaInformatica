package stream;

import java.math.BigDecimal;
import java.util.*;

import jakarta.persistence.EntityManager;
import org.iesbelen.tiendainformatica.dao.FabricanteDAO;
import org.iesbelen.tiendainformatica.dao.ProductoDAO;
import org.iesbelen.tiendainformatica.entity.Fabricante;
import org.iesbelen.tiendainformatica.dao.FabricanteDAOImpl;
import org.iesbelen.tiendainformatica.entity.Producto;
import org.iesbelen.tiendainformatica.dao.ProductoDAOImpl;
import org.iesbelen.tiendainformatica.util.JPAUtil;
import org.junit.jupiter.api.*;
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
            productosDAO.create(new Producto(asus, "Monitor 27 LED Full HD", 199.25));
            productosDAO.create(new Producto(asus, "Monitor 24 LED Full HD", 119.99));

            Fabricante lenovo = new Fabricante("Lenovo");
            fabricantesDAO.create(lenovo);
            productosDAO.create(new Producto(lenovo, "Portátil IdeaPad 320", 359.65));
            productosDAO.create(new Producto(lenovo, "Portátil Yoga 520", 452.79));

            Fabricante hp = new Fabricante("Hewlett-Packard");
            fabricantesDAO.create(hp);
            productosDAO.create(new Producto(hp, "Impresora HP Deskjet 3720", 59.99));
            productosDAO.create(new Producto(hp, "Impresora HP Laserjet Pro M26nw", 111.86));

            Fabricante samsung = new Fabricante("Samsung");
            fabricantesDAO.create(samsung);
            productosDAO.create(new Producto(samsung, "Disco SSD 1 TB", 72.99));

            Fabricante seagate = new Fabricante("Seagate");
            fabricantesDAO.create(seagate);
            productosDAO.create(new Producto(seagate, "Disco duro SATA3 1TB", 38.49));

            Fabricante crucial = new Fabricante("Crucial");
            fabricantesDAO.create(crucial);
            productosDAO.create(new Producto(crucial, "GeForce GTX 1080 Xtreme", 611.55));
            productosDAO.create(new Producto(crucial, "Memoria RAM DDR4 8GB", 24.19));

            Fabricante gigabyte = new Fabricante("Gigabyte");
            fabricantesDAO.create(gigabyte);
            productosDAO.create(new Producto(gigabyte, "GeForce GTX 1050Ti", 319.19));

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

        List<Producto> listProd = productosDAO.findAll();

       List<String> listaNombrePrecio = listProd.stream().map(prod -> prod.getNombre() + " - " + prod.getPrecio()).toList();

       listaNombrePrecio.forEach(System.out::println);

    }


    @Test
    void test2() {
        //2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares.
        List<Producto> listProd = productosDAO.findAll();

        List<String> listaEruoADolar = listProd.stream().
                map(prod -> prod.getNombre() +
                        " - Fab= " + prod.getFabricante().getIdFabricante() +
                        " - IdProduc= " + prod.getIdProducto() +
                        " - PrecioDolar= " + new BigDecimal(prod.getPrecio() * 1.17))
                .toList();
        listaEruoADolar.forEach(System.out::println);

    }


    @Test
    void test3() {
        //3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.

        List<Producto> listProd = productosDAO.findAll();
        List<String> listaNombrePrecioMayus = listProd.stream()
                .map(prod -> prod.getNombre().toUpperCase() + " - " + prod.getPrecio())
                .toList();
        listaNombrePrecioMayus.forEach(System.out::println);


    }


    @Test
    void test4() {
//4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del
//nombre del fabricante.
        List<Fabricante> listFab = fabricantesDAO.findAll();
        List<String> listaNomFab2PrimerCaracter = listFab.stream()
            .map(fab -> fab.getNombre().substring(0,2).toUpperCase())
            .toList();

        listaNomFab2PrimerCaracter.forEach(System.out::println);

    }


    @Test
    void test5() {
//5. Lista el código de los fabricantes que tienen productos.
        List<Fabricante> listFab = fabricantesDAO.findAll();
// NO ESTÁ BIEN
        List<Integer> listCodFabConProductos = listFab.stream().filter(fab -> (fab.getProductos() != null) && (!fab.getProductos().isEmpty()))
            .map(Fabricante::getIdFabricante).toList();
        listCodFabConProductos.forEach(System.out::println);
// NO ESTÁ BIEN
    }


    @Test
    void test6() {
//6. Lista los nombres de los fabricantes ordenados de forma descendente.
        List<Fabricante> listFab = fabricantesDAO.findAll();
        List<String> listaOrden = listFab.stream().sorted(Comparator.comparing(Fabricante::getNombre).reversed())
                .map(Fabricante::getNombre).toList();

        listaOrden.forEach(System.out::println);

    }


    @Test
    void test7() {
// 7. Lista los nombres de los productosDAOImpl ordenados en primer lugar por el nombre de forma ascendente y
// en segundo lugar por el precio de forma descendente.
        List<Producto> listProd = productosDAO.findAll();

        List<String> listNomOrdAscYDesc = listProd.stream().
                sorted(Comparator.comparing(Producto::getNombre).
                        thenComparing(Comparator.comparing(Producto::getPrecio).
                                reversed())).
                map(Producto::getNombre).toList();

            listNomOrdAscYDesc.forEach(System.out::println);

    }


    @Test
    void test8() {
//8. Devuelve una lista con los 5 primeros fabricantes.

        List<Fabricante> listFab = fabricantesDAO.findAll();

        List<String> lista5PrimerosFab = listFab.stream().
                map(fab -> fab.getIdFabricante() + " - " + fab.getNombre()).limit(5)
                .toList();

        lista5PrimerosFab.forEach(System.out::println);

    }


    @Test
    void test9() {
// 9. Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también
// se debe incluir en la respuesta.

        List<Fabricante> listFab = fabricantesDAO.findAll();

        List<String> fabricantes2 = listFab.stream().skip(3).limit(2).
                map(fab -> fab.getIdFabricante() + " - " + fab.getNombre()).toList();

        fabricantes2.forEach(System.out::println);
    }


    @Test
    void test10() {
// 10. Lista el nombre y el precio del producto más barato
        List<Producto> listProd = productosDAO.findAll();

        List<String> productoBarat = listProd.stream().min(Comparator.comparing(Producto::getPrecio)).
                map(prod -> prod.getNombre() + " - " + prod.getPrecio()).stream().toList();
        productoBarat.forEach(System.out::println);

    }

    @Test
    void test11() {
// 11. Lista el nombre y el precio del producto más caro
        List<Producto> listProd = productosDAO.findAll();

        List<String> prodCaro = listProd.stream().max(Comparator.comparing(Producto::getPrecio)).
                map(prod -> prod.getNombre() + " - " + prod.getPrecio()).
                stream().toList();

        prodCaro.forEach(System.out::println);
    }


    @Test
    void test12() {
//12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
        List<Producto> listProd = productosDAO.findAll();

        List<String> listCod2 = listProd.stream().
                filter(prod -> prod.getFabricante().getIdFabricante().equals(2)).
                map(producto -> producto.getNombre() + " - " + producto.getFabricante().getIdFabricante()).
                toList();
        listCod2.forEach(System.out::println);
    }


    @Test
    void test13() {
//13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
        List<Producto> listProd = productosDAO.findAll();

        List<String> prodPrecioMenos120 = listProd.stream().
                filter(prod -> prod.getPrecio() <= 120).
                map(prod -> prod.getNombre() + " - " + prod.getPrecio()).
                toList();
        prodPrecioMenos120.forEach(System.out::println);
    }


    @Test
    void test14() {
//14. Lista los productos que tienen un precio mayor o igual a 400€.
        List<Producto> listProd = productosDAO.findAll();

        List<String> listPrecioMayor400 = listProd.stream().filter(prod -> prod.getPrecio() >= 400).
        map(prod -> prod.getNombre() + " - " + prod.getPrecio()).toList();

        listPrecioMayor400.forEach(System.out::println);
    }


    @Test
    void test15() {
// 15. Lista todos los productos que tengan un precio entre 80€ y 300€.
        List<Producto> listProd = productosDAO.findAll();

        List<String> listprecioEntre80Y300 = listProd.stream().
        filter(prod -> prod.getPrecio() >= 80 && prod.getPrecio() <= 300).
        map(prod -> prod.getNombre() + " - " + prod.getPrecio()).toList();

        listprecioEntre80Y300.forEach(System.out::println);
    }


    @Test
    void test16() {
//16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
        List<Producto> listProd = productosDAO.findAll();

        List<String> precioMayor200Codigo6 = listProd.stream().
                filter(prod -> prod.getPrecio() > 200 && prod.getFabricante().getIdFabricante() == 6).
                map(prod -> prod.getNombre() + " - " + prod.getPrecio()).toList();

        precioMayor200Codigo6.forEach(System.out::println);

    }


    @Test
    void test17() {
// 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes
// para filtrar.
        List<Producto> listProd = productosDAO.findAll();

        //List<String> prodCod135

    }


    @Test
    void test18() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }



    @Test
    void test19() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test20() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test21() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test22() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test23() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test24() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test25() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test26() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test27() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test28() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test29() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test30() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }



    @Test
    void test31() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test32() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test33() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test34() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test35() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test36() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test37() {

        List<Producto> listProd = productosDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test38() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test39() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test40() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test41() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test42() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test43() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test44() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }


    @Test
    void test45() {

        List<Fabricante> listFab = fabricantesDAO.findAll();

        //TODO STREAMS

    }
}