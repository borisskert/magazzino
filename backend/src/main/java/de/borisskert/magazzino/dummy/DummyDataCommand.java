package de.borisskert.magazzino.dummy;

import de.borisskert.magazzino.person.Person;
import de.borisskert.magazzino.person.PersonRepository;
import de.borisskert.magazzino.security.Role;
import de.borisskert.magazzino.product.Product;
import de.borisskert.magazzino.product.ProductRepository;
import de.borisskert.magazzino.shoppingcart.persistence.ShoppingCart;
import de.borisskert.magazzino.shoppingcart.persistence.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("dev")
public class DummyDataCommand implements ApplicationRunner {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final PersonRepository personRepository;

    @Autowired
    public DummyDataCommand(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, PersonRepository personRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.personRepository = personRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createProducts();
        createPersons();
        createShoppingCarts();
    }

    private Product productAmdRyzen9;
    private Product productIntelCoreI9;
    private Product productMsiAm5Mainboard;
    private Product productAsRockZ790Mainboard;
    private Product productCorsairVengeanceLPX;
    private Product productKingstonRam;
    private Product productSamsung990Pro;
    private Product productNvidiaGeForceRTX4090;
    private Product productAsRockRadeonRX;
    private Product productBeQuietDarkPowerPro12;
    private Product productNzxtMidiTower;

    private void createProducts() {
        productAmdRyzen9 = productRepository.save(createProductAmdRyzen9());
        productIntelCoreI9 = productRepository.save(createIntelCoreI9());
        productMsiAm5Mainboard = productRepository.save(createMsiAm5Mainboard());
        productAsRockZ790Mainboard = productRepository.save(createAsRockZ790Mainboard());
        productCorsairVengeanceLPX = productRepository.save(createCorsairVengeanceLPX());
        productKingstonRam = productRepository.save(createKingstonRam());
        productSamsung990Pro = productRepository.save(createSamsung990Pro());
        productNvidiaGeForceRTX4090 = productRepository.save(createNvidiaGeForceRTX4090());
        productAsRockRadeonRX = productRepository.save(createAsRockRadeonRX());
        productBeQuietDarkPowerPro12 = productRepository.save(createBeQuietDarkPowerPro12());
        productNzxtMidiTower = productRepository.save(createNzxtMidiTower());
    }

    private Product createProductAmdRyzen9() {
        Product product = new Product();
        product.setName("AMD Ryzen 9 7950X3D 16x 4.20GHz So.AM5 WOF");
        product.setDescription("""
                Seit dem 02.02.2023 im Sortiment

                Holen Sie sich mit dem AMD Ryzen™ 9 7950X3D Gaming-Prozessor den ultimativen Prozessor fürs Gaming mit AMD 3D V-Cache™ Technologie für noch mehr Gaming-Performance nach Hause.

                Der 16-Kern-Prozessor mit 32 Threads, der alles kann - mit sagenhafter Performance von AMD für die anspruchsvollsten Gamer und Creator. Sie profitieren darüber hinaus von den Vorteilen der AMD 3D V-Cache™ Technologie der nächsten Generation für niedrige Latenzzeiten und noch mehr Gaming-Performance.

                ****ACHTUNG:****
                Die Verwendung der CPU ist nur in Verbindung der Mainboards mit AMD AM5 Sockel möglich. Bitte prüfen Sie die Kompatibilität auf der Herstellerseite.""");
        product.setPrice(529.99);
        product.setNumber("75433");
        return product;
    }

    private Product createIntelCoreI9() {
        Product product = new Product();
        product.setName("Intel Core i9 14900KF 24 (8+16) 3.20GHz So.1700 WOF");
        product.setDescription("""
                Seit dem 02.02.2023 im Sortiment

                Der Intel Core i9-12900K ist ein 16-Kern-Prozessor der 12. Generation und gehört zur Alder Lake-Serie. Er besitzt 24 Threads und taktet mit 3,2 GHz. Der Prozessor unterstützt DDR5-6400-Speicher und bietet 30 MB Intel Smart Cache. Der Core i9-12900K ist für den Sockel 1700 ausgelegt und wird ohne Kühler geliefert.""");
        product.setPrice(575.84);
        product.setNumber("76398");
        return product;
    }

    private Product createMsiAm5Mainboard() {
        Product product = new Product();
        product.setName("MSI Gaming Plus WIFI AMD X670E So.AM5 DDR5 ATX Retail");
        product.setDescription("""
                Seit dem 02.02.2023 im Sortiment

                Das MSI MAG X670 TOMAHAWK WIFI ist ein Mainboard im ATX-Format und bietet Platz für AMD-Prozessoren mit AM5-Sockel. Es unterstützt DDR5-Arbeitsspeicher und verfügt über zwei PCIe-4.0-x16-Slots. Das Mainboard bietet zudem zwei M.2-Slots und sechs SATA-Ports. Die Anschlussmöglichkeiten umfassen USB 3.2 Gen 2, USB 3.2 Gen 1 und USB 2.0.""");
        product.setPrice(245.80);
        product.setNumber("9120291");
        return product;
    }

    private Product createAsRockZ790Mainboard() {
        Product product = new Product();
        product.setName("ASRock Pro RS Intel Z790 So.1700 DDR5 ATX Retail");
        product.setDescription("""
                Seit dem 14.10.2022 im Sortiment

                Das ASRock Z790 PRO RS DDR5 besticht durch sein elegantes Design mit RGB Beleuchtung und seine überzeugende Leistung. Das Mainboard ist ausgestattet mit Dr.MOS, einer integrierten Leistungsstufenlösung, die für synchrone Abwärtsspannungsanwendungen optimiert ist. Im Vergleich zu herkömmlichen diskreten MOSFETs liefert er auf intelligente Weise einen höheren Strom für jede Phase und bietet so ein verbessertes thermisches Ergebnis und eine überragende Leistung.

                Mit robusten Komponenten und reibungsloser Leistungsabgabe an die CPU bietet das Z790 PRO RS unübertroffene Übertaktungsfunktionen und verbesserte Leistung bei niedrigster Temperatur.

                Im Vergleich zu herkömmlichen PCIe-Steckplätzen im DIP-Stil verbessert der PCIe-Steckplatz vom SMT-Typ den Signalfluss und maximiert die Stabilität bei hoher Geschwindigkeit, ein wichtiger Durchbruch, um die Geschwindigkeit des neuesten PCIe 5.0-Standards vollständig zu unterstützen. Der neueste PCI Express 5.0 ist in der Lage, eine atemberaubende Bandbreite von 128 GB/s bereitzustellen, um das volle Potenzial zukünftiger High-End-Grafikkarten auszuschöpfen.

                Das ATX-Mainboard ist mit dem Chipsatz Intel Z790 und somit dem Socket LGA 1700 ausgestattet. Daher eignet sich das ASRock Z790 PRO RS DDR5 für Desktop-Prozessoren der 12. und 13. Generation Intel® Core™ Prozessoren. Unterstützt werden DDR5-Arbeitsspeichr mit bis zu 6.800 MHz. Dank seiner herausragenden Spannungsversorgung, eignet sich das Mainboard optimal für Overclocking.

                Die Verwendung des Mainboards ist nur in Verbindung mit dem Intel® Core™ Chipsatz der 12. und 13. Generation möglich. Bitte prüfen Sie die Kompatibilität auf der Herstellerseite. Beim Kauf eines Mainboards bieten wir optional eine BIOS-Aktualisierung an und bringen die Plattform auf den neuesten Stand.

                Bitte beachten Sie, dass dieses Mainboard nur mit DDR5 Arbeitsspeicher (RAM) kompatibel ist.""");
        product.setPrice(349.90);
        product.setNumber("9120292");
        return product;
    }

    private Product createCorsairVengeanceLPX() {
        Product product = new Product();
        product.setName("Corsair Vengeance LPX 32GB DDR5-6400 DIMM CL36 Dual Kit");
        product.setDescription("""
                Seit dem 02.02.2023 im Sortiment

                Das Corsair Vengeance LPX 32GB DDR5-6400 DIMM CL36 Dual Kit besteht aus zwei 16-GB-DDR5-6400-Speichermodulen (PC5-51200) aus der Vengeance LPX-Serie. Die Gesamtkapazität beträgt 32 GB. Die Module unterstützen Timings von 36-36-36-76 bei 6400 MHz und benötigen 1,35 Volt Spannung. Die Vengeance LPX-Serie ist für eine hohe Leistung und Stabilität bekannt und wird von Corsair speziell für Overclocking-Enthusiasten und Gamer entwickelt.""");
        product.setPrice(299.90);
        product.setNumber("9120293");
        return product;
    }

    private Product createKingstonRam() {
        Product product = new Product();
        product.setName("64GB Kingston FURY Beast DDR5-5200 DIMM CL40 Dual Kit");
        product.setDescription("""
                Seit dem 22.03.2022 im Sortiment

                Kingston FURY™ Beast DDR5 bringt die neueste Technologie für Gaming-Plattformen der nächsten Generation. Für eine weitere Steigerung von Geschwindigkeit, Kapazität und Zuverlässigkeit verfügt DDR5 über ein ganzes Arsenal verbesserter Funktionen, wie On-Die ECC (ODECC) für verbesserte Stabilität bei extremen Geschwindigkeiten, zwei 32 Bit-Subkanäle für höhere Effizienz und einen integrierten Schaltkreis (PMIC) für die Energieverwaltung auf dem Modul, der dort Strom liefert, wo er am meisten benötigt wird.

                Überlegene Fortschritte bei der Geschwindigkeit mit der Verdoppelung der Bänke von 16 auf 32 sowie der Verdoppelung der Burst-Länge von 8 auf 16 bringen DDR5 Speicher, Ihr Gaming-Erlebnis und Ihre gesamten Systemanwendungen auf die nächste Leistungsstufe. Ganz gleich, ob beim Spielen mit extremen Einstellungen, beim Live-Streaming in 4K+ oder bei großen Animationen und 3D-Rendering, Kingston FURY Beast DDR5 der Speicher ist die notwendige Steigerung, die Stil und Leistung nahtlos miteinander verbindet. Außerdem nutzt er Intel® XMP 3.0, eine neue Übertaktungsspezifikation, die zwei anpassbare Profile für Geschwindigkeiten und Timings umfasst. Mit einer aggressiven Startgeschwindigkeit von 5600 MHz ist DDR5 50% schneller als DDR4.""");
        product.setPrice(200.37);
        product.setNumber("9053295");
        return product;
    }

    private Product createSamsung990Pro() {
        Product product = new Product();
        product.setName("4TB Samsung 990 PRO M.2 2280 PCIe 4.0 x4 3D-NAND TLC (MZ-V9P4T0BW)");
        product.setDescription("""
                Seit dem 13.09.2023 im Sortiment

                Explosives Tempo für intensives GamingEntdecke die starke Leistung mit PCIe® 4.0 und erlebe rasante Geschwindigkeit, um dich der Konkurrenz zu stellen. Die intelligente Wärmeregulierung des Samsung Controllers bietet hohe Energieeffizienz bei gleichbleibend hoher Geschwindigkeit und Performance. So steht dem Gaming-Spaß nichts mehr im Weg.PCIe® mit rasanter GeschwindigkeitDie 990 PRO bietet sequenzielle Lese-/Schreibgeschwindigkeiten von bis zu 7.450 / 6.900 MB/s. Damit ist beinahe das theoretische Maximum des PCIe® 4.0-Standards erreicht. Sie ist 40% und 55% schneller als die 980 PRO und erreicht bis zu 1.400K / 1.550K IOPS. Das 4-TB-Modell kommt sogar auf bis zu 1.600K IOPS.Bahnbrechende EnergieeffizienzHöhere Leistung verbraucht oftmals mehr Strom. Nicht mit der 990 PRO. Erlebe einen geringen Strombedarf durch eine um über 50 % pro Watt verbesserte Leistung gegenüber dem Vorgängermodell.Smarte KühlungDer nickelbeschichtete Controller und der hochmoderne thermische Steueralgorithmus regulieren die Wärme und ermöglichen so uneingeschränkte Performance. Das Heatspreader-Label kontrolliert die Wärme des NAND-Chips, während die Dynamic-Thermal Guard-Technologie die Temperaturen reguliert.Wecke den Profi-Gamer in dirSetze alles auf Sieg mit der 990 PRO. Das 4-TB-Modell unterstützt dich mit zufälligen Lesegeschwindigkeiten von bis zu 1.600K IOPS. Genieße schnelle Ladezeiten auf deinem PC oder deiner Playstation 5 und lass die SSD frischen Wind in deine Spiele bringen.Samsung Magician SoftwareFortschrittliche Laufwerksverwaltung einfach gemacht - mit der Samsung Magician-Software. Die benutzerfreundliche Software-Lösung hilft dir dabei, immer ein Auge auf deine SSD zu haben. Damit kannst du dein Laufwerk mit Updates immer auf dem neusten Stand halten, Statusparameter und Geschwindigkeit überwachen und sogar die Leistungsfähigkeit optimieren. Es wird empfohlen, die Firmware deiner Samsung SSDs immer auf die neueste Version zu aktualisieren.""");
        product.setPrice(308.90);
        product.setNumber("76276");
        return product;
    }

    private Product createNvidiaGeForceRTX4090() {
        Product product = new Product();
        product.setName("24GB MSI GeForce RTX 4090 Gaming X Slim Aktiv PCIe 4.0 x16 (Retail)");
        product.setDescription("""
                Seit dem 15.11.2023 im Sortiment

                Die GAMING SLIM-Serie ist eine dünnere Variante der GAMING-Serie und bietet gleichzeitig hohe Leistungsfähigkeit und ein aggressives Aussehen.
                Problemlose Kompatibilität
                Das leichtere Design ist für diejenigen gedacht, die ein Gaming-Rig mit Platzmangel bauen.

                Steigern Sie die Leistung mit der TRI FROZR 3-Kühlung und ihren Hauptfunktionen - TORX FAN 5.0, Core Pipe und Airflow Control.

                Bleib cool und ruhig.

                Das thermische Design TRI FROZR 3 von MSI verbessert die Wärmeableitung rund um die Grafikkarte.""");
        product.setPrice(1848.99);
        product.setNumber("76515");
        return product;
    }

    private Product createAsRockRadeonRX() {
        Product product = new Product();
        product.setName("24GB ASRock Radeon RX 7900 XTX Taichi OC Aktiv PCIe 4.0 x16 (Retail)");
        product.setDescription("""
                Seit dem 05.01.2023 im Sortiment

                Erleben Sie noch nie dagewesene Performance, Bilddarstellung und Effizienz bei 4K und darüber hinaus mit AMD Radeon™ RX 7000-Serie Grafikkarten, den weltweit ersten Gaming-GPUs mit AMD RDNA™ 3 Chiplet-Technologie. Tauchen Sie in atemberaubende Grafik ein mit der punktgenauen Farbtreue der AMD Radiance Display™ Engine und boosten Sie Framerates mit AMD FidelityFX™ Super Resolution und Radeon™ Super Resolution Upscaling-Technologien.

                Eine hohe Grafikperformance benötigt auch eine effektive Kühllösung. Die ASRock Radeon RX 7900 XTX TAICHI OC verwendet daher das TAICHI 3X Kühlsystem. Konzipiert um die beste Balance zwischen thermischer Effizienz und Geräuschlosigkeit zu schaffen.

                Die neu entwickelten Striped Ring Lüfter von ASRock erhalten mehr seitlichen Einlass und sorgen somit für einen besseren Luftstrom durch das Kühlarray. Grundsätzlich wurde viel Wert auf eine optimale Kühllösung gelegt, sodass intensiven Spieleanwendungen und Gefechte nichts im Wege steht. Hierfür kommen genau darauf abgestimmte Memory Heatpipes, Premium Thermal Pads und viele weitere Highlights zum Einsatz.

                Neben den hochwertigen Komponenten, ist die Grafikkarte zudem mit einem verstärkten Metallrahmen versehen. Dieser verbessert die Struktur, um zu verhindern, dass das PCB gebogen wird.

                Dank ARGB-LEDs können Sie Ihre eigenen einzigartigen Lichteffekte erzeugen. Schalten Sie diese nach Belieben an und aus. Die Grafikkarte kann darüber hinaus auch mit Ihrem ASRock-Mainboard synchronisiert werden, die Polychrome SYNC unterstützen.""");
        product.setPrice(990.99);
        product.setNumber("9082271");
        return product;
    }

    private Product createBeQuietDarkPowerPro12() {
        Product product = new Product();
        product.setName("850 Watt be quiet! Pure Power 12 M Modular 80+ Gold");
        product.setDescription("""
                Seit dem 31.01.2023 im Sortiment

                AUSSERORDENTLICH LEISE, ÜBERLEGENE FEATURES
                Pure Power 12 M 850W ist ATX 3.0 und PCIe 5.0 kompatibel und bietet unvergleichliche Zuverlässigkeit mit erstklassigen Features. Mit einem PCIe 5.0-Kabel und PCIe 6+2-Kabeln im Lieferumfang ermöglicht dieses Netzteil herausragende Kompatibilität sowohl mit aktuellen als auch Next-Gen Grafikkarten. Pure Power 12 M 850W bietet die bestmögliche Kombination von Features in seiner Klasse.

                - 80 PLUS® Gold Effizienz (von bis zu 93,2%)
                - ATX 3.0-Netzteil mit voller Unterstützung für PCIe 5.0 GPUs und GPUs mit PCIe 6+2 Anschlüssen
                - Verarbeitet hohe Lastspitzen
                - Außerordentlich leiser 120mm be quiet! Lüfter
                - Unübertroffene Signalstabilität und starke Spannungsregulierung dank LLC-Technologie
                - 2 starke 12V-Leitungen
                - Modulare Kabel für maximale Flexibilität
                - Ein PCIe 5.0 12VHPWR-Anschluss mit 600W und 4 PCIe 6+2-Anschlüsse für übertaktete High-End GPUs
                - Umfassender Schutz der wertvollen PC-Komponenten
                - Erfüllt die Voraussetzungen für Modern Standby""");
        product.setPrice(114.80);
        product.setNumber("9084274");
        return product;
    }

    private Product createNzxtMidiTower() {
        Product product = new Product();
        product.setName("NZXT H9 Flow Midi Tower ohne Netzteil schwarz");
        product.setDescription("""
                 NZXT H9 Flow - CM-H91FB-01 - Premium ATX Mid-Tower PC Gaming Gehäuse - Zwei Kammer Design - Airflow Top Panel - Wasserkühlung ready - Schwarz

                Die H9-Serie wurde entwickelt, um mit ihren großen thermischen Fähigkeiten leistungsstarke GPUs zu kühlen. Sie bietet Platz für zehn Lüfter sowie zahlreiche Montageoptionen für 360-mm-Radiatoren, und die perforierte Oberseite sorgt für zusätzliche Kühlung.

                Die Vorteile der H9 Flow Serie

                    Einzigartige durchgängige Glasscheibe für einen ungehinderten Blick auf das Innenleben
                    Intuitives Kabelmanagement und Zwei-Kammer-Design
                    Platz für zehn Lüfter oder drei 360-mm-Radiatoren
                    Perforierte Oberseite für noch bessere Kühlung
                    Direkter Luftstrom zur GPU für die Unterstützung leistungsstarker Bauteile
                    Verbesserte Kompatibilität mit anderen Produkten aus dem NZXT-Sortiment
                    Mit vier 120 mm Quiet Airflow-Lüftern der F-Serie

                """);
        product.setPrice(136.00);
        product.setNumber("75408");
        return product;
    }

    private Person alice;
    private Person bob;
    private Person charlie;
    private Person david;
    private Person eve;
    private Person frank;
    private Person grace;
    private Person henry;
    private Person ivy;
    private Person jack;

    private void createPersons() {
        alice = personRepository.save(createPersonAlice());
        bob = personRepository.save(createPersonBob());
        charlie = personRepository.save(createPersonCharlie());
        david = personRepository.save(createPersonDavid());
        eve = personRepository.save(createPersonEve());
        frank = personRepository.save(createPersonFrank());
        grace = personRepository.save(createPersonGrace());
        henry = personRepository.save(createPersonHenry());
        ivy = personRepository.save(createPersonIvy());
        jack = personRepository.save(createPersonJack());
    }

    private Person createPersonAlice() {
        Person person = new Person();
        person.setFirstName("Alice");
        person.setLastName("Smith");
        person.setEmail("alice.smith@gmail.com");
        person.setRole(Role.CUSTOMER);

        return person;
    }

    private Person createPersonBob() {
        Person person = new Person();
        person.setFirstName("Bob");
        person.setLastName("Doe");
        person.setEmail("bob.d0e@gmail.com");
        person.setRole(Role.CUSTOMER);

        return person;
    }

    private Person createPersonCharlie() {
        Person person = new Person();
        person.setFirstName("Charlie");
        person.setLastName("Brown");
        person.setEmail("cb@peanuts.org");
        person.setRole(Role.CUSTOMER);

        return person;
    }

    private Person createPersonDavid() {
        Person person = new Person();
        person.setFirstName("David");
        person.setLastName("Miller");
        person.setEmail("miller@atandt.com");
        person.setRole(Role.CUSTOMER);

        return person;
    }

    private Person createPersonEve() {
        Person person = new Person();
        person.setFirstName("Eve");
        person.setLastName("Johnson");
        person.setEmail("eveve@yahoo.com");
        person.setRole(Role.CUSTOMER);

        return person;
    }

    private Person createPersonFrank() {
        Person person = new Person();
        person.setFirstName("Frank");
        person.setLastName("Miller");
        person.setEmail("fm@live.com");
        person.setRole(Role.CUSTOMER);

        return person;
    }

    private Person createPersonGrace() {
        Person person = new Person();
        person.setFirstName("Grace");
        person.setLastName("Smith");
        person.setEmail("grace@smith.org");
        person.setRole(Role.CUSTOMER);

        return person;
    }

    private Person createPersonHenry() {
        Person person = new Person();
        person.setFirstName("Henry");
        person.setLastName("Johnson");
        person.setEmail("johnson@ccc.org");
        person.setRole(Role.CUSTOMER);

        return person;
    }

    private Person createPersonIvy() {
        Person person = new Person();
        person.setFirstName("Ivy");
        person.setLastName("Brown");
        person.setEmail("ivy@bridge.com");
        person.setRole(Role.CUSTOMER);

        return person;
    }

    private Person createPersonJack() {
        Person person = new Person();
        person.setFirstName("Jack");
        person.setLastName("Sheppard");
        person.setEmail("jack@sheppard.com");
        person.setRole(Role.CUSTOMER);

        return person;
    }

    private void createShoppingCarts() {
        shoppingCartRepository.save(createShoppingCartAlice());
        shoppingCartRepository.save(createShoppingCartBob());
        shoppingCartRepository.save(createShoppingCartCharlie());
        shoppingCartRepository.save(createShoppingCartDavid());
        shoppingCartRepository.save(createShoppingCartEve());
        shoppingCartRepository.save(createShoppingCartFrank());
        shoppingCartRepository.save(createShoppingCartGrace());
        shoppingCartRepository.save(createShoppingCartHenry());
        shoppingCartRepository.save(createShoppingCartIvy());
        shoppingCartRepository.save(createShoppingCartJack());
    }

    private ShoppingCart createShoppingCartAlice() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(alice);
        shoppingCart.addItem(productAmdRyzen9, 1L);
        shoppingCart.addItem(productMsiAm5Mainboard, 1L);
        shoppingCart.addItem(productCorsairVengeanceLPX, 2L);
        shoppingCart.addItem(productSamsung990Pro, 2L);
        shoppingCart.addItem(productNvidiaGeForceRTX4090, 1L);
        shoppingCart.addItem(productBeQuietDarkPowerPro12, 1L);
        shoppingCart.addItem(productNzxtMidiTower, 1L);
        shoppingCart.setCheckedOut(false);

        return shoppingCart;
    }

    private ShoppingCart createShoppingCartBob() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(bob);
        shoppingCart.addItem(productIntelCoreI9, 1L);
        shoppingCart.addItem(productAsRockZ790Mainboard, 1L);
        shoppingCart.addItem(productKingstonRam, 2L);
        shoppingCart.addItem(productAsRockRadeonRX, 1L);
        shoppingCart.addItem(productBeQuietDarkPowerPro12, 1L);
        shoppingCart.addItem(productNzxtMidiTower, 1L);
        shoppingCart.setCheckedOut(true);

        return shoppingCart;
    }

    private ShoppingCart createShoppingCartCharlie() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(charlie);
        shoppingCart.addItem(productAmdRyzen9, 3L);
        shoppingCart.setCheckedOut(true);

        return shoppingCart;
    }

    private ShoppingCart createShoppingCartDavid() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(david);
        shoppingCart.addItem(productIntelCoreI9, 1L);
        shoppingCart.addItem(productCorsairVengeanceLPX, 2L);
        shoppingCart.addItem(productNvidiaGeForceRTX4090, 1L);
        shoppingCart.setCheckedOut(false);

        return shoppingCart;
    }

    private ShoppingCart createShoppingCartEve() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(eve);
        shoppingCart.addItem(productMsiAm5Mainboard, 1L);
        shoppingCart.addItem(productKingstonRam, 2L);
        shoppingCart.setCheckedOut(false);

        return shoppingCart;
    }

    private ShoppingCart createShoppingCartFrank() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(frank);
        shoppingCart.addItem(productAmdRyzen9, 1L);
        shoppingCart.addItem(productMsiAm5Mainboard, 1L);
        shoppingCart.addItem(productCorsairVengeanceLPX, 2L);
        shoppingCart.addItem(productSamsung990Pro, 2L);
        shoppingCart.addItem(productNvidiaGeForceRTX4090, 1L);
        shoppingCart.addItem(productBeQuietDarkPowerPro12, 1L);
        shoppingCart.addItem(productNzxtMidiTower, 1L);
        shoppingCart.setCheckedOut(false);

        return shoppingCart;
    }

    private ShoppingCart createShoppingCartGrace() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(grace);
        shoppingCart.addItem(productIntelCoreI9, 1L);
        shoppingCart.addItem(productAsRockZ790Mainboard, 1L);
        shoppingCart.addItem(productKingstonRam, 2L);
        shoppingCart.addItem(productAsRockRadeonRX, 1L);
        shoppingCart.addItem(productBeQuietDarkPowerPro12, 1L);
        shoppingCart.addItem(productNzxtMidiTower, 1L);
        shoppingCart.setCheckedOut(true);

        return shoppingCart;
    }

    private ShoppingCart createShoppingCartHenry() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(henry);
        shoppingCart.addItem(productAmdRyzen9, 3L);
        shoppingCart.setCheckedOut(true);

        return shoppingCart;
    }

    private ShoppingCart createShoppingCartIvy() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(ivy);
        shoppingCart.addItem(productIntelCoreI9, 1L);
        shoppingCart.addItem(productCorsairVengeanceLPX, 2L);
        shoppingCart.addItem(productNvidiaGeForceRTX4090, 1L);
        shoppingCart.setCheckedOut(false);

        return shoppingCart;
    }

    private ShoppingCart createShoppingCartJack() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(jack);
        shoppingCart.addItem(productMsiAm5Mainboard, 1L);
        shoppingCart.addItem(productKingstonRam, 2L);
        shoppingCart.setCheckedOut(false);

        return shoppingCart;
    }
}
