Ka�da linijka pliku grafu to nowy punkt lub po��czenie, sk�ada si� z nast�puj�cych element�w:

a) dla w�z�a linijka zaczyna si� od "*", pierwsza po niej liczba to id punktu, kt�re musi by� unikalne,
nast�pna cyfra to wsp�rz�dna X wed�ug kt�rej pojawi si� na display'u kolejna to analogicznie Y dalej 
wszystkie id w�z��w do kt�rych podany w�ze� ma mie� po��czenie zamkni�te w klamrach "{}"na ko�cu znajduje si� 
waga w�z�a, kt�ra jest opcjonaln, przyk�ad:
0 1 2   3   4 5 6 7 8 
* 1 100 200 { 1 2 } 5.4

b) dla utworzenia pe�nego grafu nie potrzeba spisu po��cze�, pomocne s� gdy chcemy uwzgl�dnia� szczeg�lny przypadek mapy, 
w kt�rej odleg�o�ci mi�dzy punktami nie s� faktycznymi wagami:

/ id A B Waga OneWay