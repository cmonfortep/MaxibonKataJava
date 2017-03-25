package com.karumi.maxibonkata;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(JUnitQuickcheck.class)
public class KarumiHQsProperties {

    @Mock
    private Chat mockedChat;

    private KarumiHQs karumiHQs;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        karumiHQs = new KarumiHQs(mockedChat);
    }

    @Property
    public void neverLessThan2MaxibonsInTheFridge(@From(DevelopersGenerator.class) Developer developer) {
        karumiHQs.openFridge(developer);

        assertFalse(karumiHQs.getMaxibonsLeft() < 2);
    }

    @Property(trials = 1000)
    public void neverLessThan2MaxibonsInTheFridgeWithNotSoHungryDevs
          (@From(NotSoHungryDevelopersGenerator.class) Developer developer) {
        System.out.println(developer);
        karumiHQs.openFridge(developer);
        System.out.println("quedan:" + karumiHQs.getMaxibonsLeft());
        assertFalse(karumiHQs.getMaxibonsLeft() < 2);
    }

    @Property(trials = 5)
    public void neverLessThan2MaxibonsInTheFridgeWithAListOfDevs
          (List<@From(NotSoHungryDevelopersGenerator.class) Developer> developer) {
        developer.stream().forEach(System.out::println);

        karumiHQs.openFridge(developer);
        System.out.println("quedan:" + karumiHQs.getMaxibonsLeft());

        assertFalse(karumiHQs.getMaxibonsLeft() < 2);
    }
}
