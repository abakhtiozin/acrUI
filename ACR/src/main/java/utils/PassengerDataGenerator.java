package main.java.utils;

import db.SQL;
import main.java.model.Supplier;
import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerDocumentType;
import main.java.model.passenger.PassengerGender;

import static main.java.model.passenger.PassengerDocumentType.*;
import static main.java.utils.DateTimeHelper.currentDatePlusNYears;
import static util.other.RandomDataGenerator.*;

/**
 * Created by Andrii.Bakhtiozin on 18.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class PassengerDataGenerator {

    private PassengerGender getRandomGender() {
        return randomNumber(0, 2) == 0 ? PassengerGender.FEMALE : PassengerGender.MALE;
    }

    public Passenger generateFor(Supplier supplier) {
        Passenger passenger = new Passenger();
        if (passenger.getGender() == null) passenger.withGender(getRandomGender());
        if (passenger.getStateIssuedDocument() == null)
            passenger.withStateIssuedDocument(SQL.getCountryCode(randomNumber(0, 190).toString()));
//        if (passenger.getBirthDate() == null)
//            passenger.withBirthDate(new DateTime().minusYears(randomNumber(3, 70)).minusMonths(randomNumber(1, 11)));

        if (passenger.getName() == null) passenger.withName("TEST" + getRandomLiteralsString(3));
        if (passenger.getSurname() == null) passenger.withSurname("TEST" + getRandomLiteralsString(4));
        if (passenger.getDocumentNumber() == null) {
            if (supplier.equals(Supplier.IP)) {
                passenger.withDocumentNumber(getRandomNumericString(7));
            } else passenger.withDocumentNumber(getRandomLiteralsString(2).concat(getRandomNumericString(6)));
        }

        if (supplier.equals(Supplier.TF)) {
            if (passenger.getDocumentExpiryDate() == null)
                passenger.withDocumentExpiryDate(currentDatePlusNYears(randomNumber(1, 9)));
        }
        if (supplier.equals(Supplier.UFS) || supplier.equals(Supplier.IP)) {
            if (passenger.getFathersName() == null) passenger.withFathersName(getRandomLiteralsString());
            if (supplier.equals(Supplier.UFS))
                if (passenger.getBirthPlace() == null) passenger.withBirthPlace(getRandomLiteralsString());
        }
        passenger.withDocumentType(Иностранный_документ);
        return passenger;
    }

    public Passenger generateFor(Supplier supplier, PassengerDocumentType documentType) {
        Passenger passenger = new Passenger();
        if (documentType.equals(Иностранный_документ)) {
            return generateFor(supplier);
        }
        passenger = generateCyrillicData(supplier, documentType, passenger);

        if (supplier.equals(Supplier.IP) || supplier.equals(Supplier.UFS)) {
            switch (documentType) {
                case Вид_на_жительство:
                    if (passenger.getDocumentNumber() == null) passenger.withDocumentNumber(getRandomNumericString(7));
                    break;
                case Паспорт_РК:
                    if (passenger.getDocumentNumber() == null)
                        passenger.withDocumentNumber(getRandomNumericString(randomNumber(7, 8)));
                    break;
                case Паспорт_Узбекистана:
                    if (passenger.getName() == null) passenger.withName(getRandomLiteralsString(randomNumber(3, 9)));
                    if (passenger.getSurname() == null)
                        passenger.withSurname(getRandomLiteralsString(randomNumber(3, 9)));
                    if (passenger.getFathersName() == null)
                        passenger.withFathersName(getRandomLiteralsString(randomNumber(3, 9)));
                    if (passenger.getDocumentNumber() == null)
                        passenger.withDocumentNumber("SS"/*getRandomLiteralsString(2)*/.concat(getRandomNumericString(7)));
                    break;
                case Удостоверение_личности_РК:
                    if (passenger.getDocumentNumber() == null) passenger.withDocumentNumber(getRandomNumericString(9));
                    break;
                case Свидетельство_о_рождении_РК:
                    if (passenger.getDocumentNumber() == null) passenger.withDocumentNumber(getRandomNumericString(7));
                    break;
                case Загранпаспорт_РФ:
                    if (passenger.getDocumentNumber() == null) passenger.withDocumentNumber(getRandomNumericString(9));
                    break;
                case Паспорт_РФ:
                    if (passenger.getDocumentNumber() == null) passenger.withDocumentNumber(getRandomNumericString(10));
                    break;
                case Военный_билет_РФ:
                    if (passenger.getDocumentNumber() == null) passenger.withDocumentNumber(getRandomNumericString(10));
                    break;
                case Паспорт_моряка:
                    if (passenger.getDocumentNumber() == null)
                        passenger.withDocumentNumber("SS"/*getRandomLiteralsString(2)*/.concat(getRandomNumericString(7)));
                    break;
                case Свидетельство_о_рождении_РФ:
                    if (passenger.getDocumentNumber() == null)
                        passenger.withDocumentNumber("X".concat(getRandomLiteralsRussianString(2, 3)).concat(getRandomNumericString(6)));
                    break;
            }
        }
        passenger.withDocumentType(documentType);
        if (passenger.getStateIssuedDocument() == null)
            passenger.withStateIssuedDocument("RU");
        return passenger;
    }

    private Passenger generateCyrillicData(Supplier supplier, PassengerDocumentType documentType, Passenger passenger) {
        if (supplier.equals(Supplier.IP) || supplier.equals(Supplier.UFS)) {
            if (documentType.equals(Вид_на_жительство) || documentType.equals(Паспорт_РК) || documentType.equals(Удостоверение_личности_РК)
                    || documentType.equals(Свидетельство_о_рождении_РК) || documentType.equals(Загранпаспорт_РФ) || documentType.equals(Паспорт_РФ)
                    || documentType.equals(Свидетельство_о_рождении_РФ) || documentType.equals(Военный_билет_РФ) || documentType.equals(Паспорт_моряка)) {
                passenger = generateCyrillicPassengerData(passenger);
            }
        }
        return passenger;
    }

    private Passenger generateCyrillicPassengerData(Passenger passenger) {
        if (passenger.getName() == null) passenger.withName(getRandomLiteralsRussianString(4, 8));
        if (passenger.getSurname() == null) passenger.withSurname(getRandomLiteralsRussianString(4, 8));
        if (passenger.getFathersName() == null) passenger.withFathersName(getRandomLiteralsRussianString(4, 8));
        if (passenger.getGender() == null) passenger.withGender(getRandomGender());
        if (passenger.getBirthPlace() == null) passenger.withBirthPlace(getRandomLiteralsRussianString(4, 8));
        return passenger;
    }

}
