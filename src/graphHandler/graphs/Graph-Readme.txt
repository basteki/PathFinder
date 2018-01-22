Ka¿da linijka pliku grafu to nowy punkt lub po³¹czenie, sk³ada siê z nastêpuj¹cych elementów:

a) dla wêz³a linijka zaczyna siê od "*", pierwsza po niej liczba to id punktu, które musi byæ unikalne,
nastêpna cyfra to wspó³rzêdna X wed³ug której pojawi siê na display'u kolejna to analogicznie Y dalej 
wszystkie id wêz³ów do których podany wêze³ ma mieæ po³¹czenie zamkniête w klamrach "{}"na koñcu znajduje siê 
waga wêz³a, która jest opcjonaln, przyk³ad:
0 1 2   3   4 5 6 7 8 
* 1 100 200 { 1 2 } 5.4

b) dla utworzenia pe³nego grafu nie potrzeba spisu po³¹czeñ, pomocne s¹ gdy chcemy uwzglêdniaæ szczególny przypadek mapy, 
w której odleg³oœci miêdzy punktami nie s¹ faktycznymi wagami:

/ id A B Waga OneWay