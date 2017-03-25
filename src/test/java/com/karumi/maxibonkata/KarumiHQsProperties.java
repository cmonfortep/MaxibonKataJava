package com.karumi.maxibonkata;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(JUnitQuickcheck.class)
public class KarumiHQsProperties {

    private Chat mockedChat;
    private KarumiHQs karumiHQs;

    @Before
    public void setUp() {
        mockedChat = mock(Chat.class);
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

    @Property(trials = 20)
    public void neverLessThan2MaxibonsInTheFridgeWithAListOfDevs
          (List<@From(NotSoHungryDevelopersGenerator.class) Developer> developer) {
        developer.stream().forEach(System.out::println);

        karumiHQs.openFridge(developer);
        System.out.println("quedan:" + karumiHQs.getMaxibonsLeft());

        assertFalse(karumiHQs.getMaxibonsLeft() < 2);
    }

    @Property(trials = 5)
    public void messageIsSentWhenThereAreLessThan2MaxibonsWithADeveloper
          (@From(HungryDevelopersGenerator.class) Developer developer) {

        karumiHQs.openFridge(developer);
        System.out.println("quedan:" + karumiHQs.getMaxibonsLeft());

        verify(mockedChat).sendMessage(anyString());
    }

    @Property(trials = 5)
    public void messageIsNotSentWhenThereAreEnoughtMaxibonsWithADeveloper
          (@From(NotSoHungryDevelopersGenerator.class) Developer developer) {

        karumiHQs.openFridge(developer);
        System.out.println("quedan:" + karumiHQs.getMaxibonsLeft());

        verify(mockedChat, never()).sendMessage(anyString());
    }
}
