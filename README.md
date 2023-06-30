# HealthDataProject

**- Σχετικά με το πρόγραμμα: -**

Το πρόγραμμα HealthDataProject φτιάχτηκε στα πλαίσια της πτυχιακής εργασίας, που έχει ως θέμα την άντληση δεδομένων υγείας σε μορφή Open mHealth JSON σχήματα, τον μετασχηματισμό τους σε RDF γράφους και την αποθήκευση τους στην σημασιολογική βάση δεδομένων GraphDB. Υλοποιεί τον αυτόματο μετασχηματισμό των δεδομένων από JSON σχήματα (Open mHealth) σε τριπλέτες RDF, την αποθήκευση τους στο GraphDB και την επαλήθευσή τους με τη χρήση της SHACL. Πιο συγκεκριμένα το πρόγραμμα περιλαμβάνει:
1.	**Την κλάση ReadBodyHeight**. Η κλάση αυτή διαβάζει τα JSON σχήματα που αφορούν το ύψος και τα μετατρέπει πρώτα σε μορφή RML και στη συνέχεια σε τριπλέτες RDF. Οι τριπλέτες που προέκυψαν αποθηκεύονται στον φάκελο FinalTTL σε μορφή .ttl με όνομα body_height_ΑριθμόςΜέτρησης_ΌνομαΑτόμου_output.ttl (π.χ. body_height_1_Nikos_output.ttl).
2.	**Την κλάση ReadBodyWeight**. Η κλάση αυτή διαβάζει τα JSON σχήματα που αφορούν το βάρος και τα μετατρέπει πρώτα σε μορφή RML και στη συνέχεια σε τριπλέτες RDF. Οι τριπλέτες που προέκυψαν αποθηκεύονται στον φάκελο FinalTTL σε μορφή .ttl με όνομα body_weight_ΑριθμόςΜέτρησης_ΌνομαΑτόμου_output.ttl (π.χ. body_weight _1_Nikos_output.ttl).
3.	**Την κλάση ReadCaloriesBurned**. Η κλάση αυτή διαβάζει τα JSON σχήματα που αφορούν τις θερμίδες που κάηκαν και τα μετατρέπει πρώτα σε μορφή RML και στη συνέχεια σε τριπλέτες RDF. Οι τριπλέτες που προέκυψαν αποθηκεύονται στον φάκελο FinalTTL σε μορφή .ttl με όνομα calories_burned_ΑριθμόςΜέτρησης_ΌνομαΑτόμου_output.ttl (π.χ. calories_burned_1_Nikos_output.ttl).
4.	**Την κλάση ReadHeartRate**. Η κλάση αυτή διαβάζει τα JSON σχήματα που αφορούν τους καρδιακούς παλμούς και τα μετατρέπει πρώτα σε μορφή RML και στη συνέχεια σε τριπλέτες RDF. Οι τριπλέτες που προέκυψαν αποθηκεύονται στον φάκελο FinalTTL σε μορφή .ttl με όνομα heart_rate_ΑριθμόςΜέτρησης_ΌνομαΑτόμου_output.ttl (π.χ. heart_rate_1_Nikos_output.ttl).
5.	**Την κλάση ReadPhysicalActivity**. Η κλάση αυτή διαβάζει τα JSON σχήματα που αφορούν τις δραστηριότητες και τα μετατρέπει πρώτα σε μορφή RML και στη συνέχεια σε τριπλέτες RDF. Οι τριπλέτες που προέκυψαν αποθηκεύονται στον φάκελο FinalTTL σε μορφή .ttl με όνομα physical_activity_ΑριθμόςΜέτρησης_ΌνομαΑτόμου_output.ttl (π.χ. physical_activity_1_Nikos_output.ttl).
6.	**Την κλάση ReadSpeed**. Η κλάση αυτή διαβάζει τα JSON σχήματα που αφορούν την ταχύτητα και τα μετατρέπει πρώτα σε μορφή RML και στη συνέχεια σε τριπλέτες RDF. Οι τριπλέτες που προέκυψαν αποθηκεύονται στον φάκελο FinalTTL σε μορφή .ttl με όνομα speed_ΑριθμόςΜέτρησης_ΌνομαΑτόμου_output.ttl (π.χ. speed_1_Nikos_output.ttl).
7.	**Την κλάση ReadStepCount**. Η κλάση αυτή διαβάζει τα JSON σχήματα που αφορούν τα βήματα που έγιναν και τα μετατρέπει πρώτα σε μορφή RML και στη συνέχεια σε τριπλέτες RDF. Οι τριπλέτες που προέκυψαν αποθηκεύονται στον φάκελο FinalTTL σε μορφή .ttl με όνομα step_count_ΑριθμόςΜέτρησης_ΌνομαΑτόμου_output.ttl (π.χ. step_count_1_Nikos_output.ttl).
8.	**Την κλάση RMLMapper**. Η κλάση αυτή μετατρέπει τα αρχεία που έχουν μορφή RML, σε τριπλέτες RDF.
9.	**Την κλάση GraphDBManager**. Η κλάση αυτή δημιουργεί ένα repository στο GraphDB χρησιμοποιώντας το αρχείο config.ttl που υπάρχει στον φάκελο GraphDBconfig, αφού πρώτα ελέγξει αν υπάρχει ήδη repository με το ίδιο όνομα. Έπειτα ανεβάζει όλα τα αρχεία που βρίσκονται στον φάκελο FinalTTL στο repository που δημιουργήθηκε.
10.	**Την κλάση ShaclValidation**. Η κλάση αυτή χρησιμοποιείται για την επαλήθευση των τριπλετών RDF που εισήχθησαν στο repository του GraphDB. Χρησιμοποιεί το αρχείο shacl.ttl που υπάρχει στον φάκελο SHACLValidation, εισάγοντάς το στο repository ποη δημιουργήθηκε.
11.	**Την κλάση TTLFilesMerger**. Η κλάση αυτή χρησιμοποιείται για την παραγωγή ενός ενιαίου .ttl αρχείου που περιλαμβάνει όλα τα .ttl αρχεία με τριπλέτες RDF που δημιουργήθηκαν. Δημιουργεί στην ουσία την τελική οντολογία, και την αποθηκεύει στον φάκελο FinalOntology με όνομα HealthOntology.ttl. Το HealthOntology.ttl χρησιμοποιείται για την προβολή της οντολογίας μέσω του προγράμματος Protégé.
12.	**Την κλάση Main**. Από αυτή τρέχουν όλες οι υπόλοιπες κλάσεις του προγράμματος με τη σειρά που περιγράφηκαν.

**Οδηγίες χρήσης:**
1. Τα δεδομένα υγείας που αφορούν το ύψος ενός ατόμου πρέπει να αποθηκευτούν στον φάκελο "HealthDataProject/schema/Body Height". Απαραίτητη προυπόθεση είναι τα αρχεία αυτά να είναι JSON σχήματα σε μορφή Open mHealth. Μέσα στον φάκελο υπάρχουν ήδη 3 JSON σχήματα που αφορούν το ύψος 3 διαφορετικών ατόμων. Εάν ο χρήστης επιθυμεί να τρέξει το πρόγραμμα χρησιμοποιώντας δικές του μετρήσεις, θα πρέπει να διαγράψει τα .JSON αρχεία που υπάρχουν μέσα στον φάκελο, και να ανεβάσει .json αρχεία που αφορούν τις δικές του μετρήσεις. Τα αρχεία που αποθηκεύονται θα πρέπει να έχουν όνομα της μορφής body_height_Αριθμός_ΌνομαΧρήστη.json (πχ body_height_1_Dimitris.json).
2. Τα δεδομένα υγείας που αφορούν το βάρος ενός ατόμου πρέπει να αποθηκευτούν στον φάκελο "HealthDataProject/schema/Body Weight". Απαραίτητη προυπόθεση είναι τα αρχεία αυτά να είναι JSON σχήματα σε μορφή Open mHealth. Μέσα στον φάκελο υπάρχουν ήδη 22 JSON σχήματα που αφορούν το βάρος 3 διαφορετικών ατόμων. Εάν ο χρήστης επιθυμεί να τρέξει το πρόγραμμα χρησιμοποιώντας δικές του μετρήσεις, θα πρέπει να διαγράψει τα .JSON αρχεία που υπάρχουν μέσα στον φάκελο, και να ανεβάσει .json αρχεία που αφορούν τις δικές του μετρήσεις. Τα αρχεία που αποθηκεύονται θα πρέπει να έχουν όνομα της μορφής body_weight_Αριθμός_ΌνομαΧρήστη.json (πχ body_weight_1_Dimitris.json).
3. Τα δεδομένα υγείας που αφορούν τις θερμίδες που έκαψε ένα ατόμο πρέπει να αποθηκευτούν στον φάκελο "HealthDataProject/schema/Calories Burned". Απαραίτητη προυπόθεση είναι τα αρχεία αυτά να είναι JSON σχήματα σε μορφή Open mHealth. Μέσα στον φάκελο υπάρχουν ήδη 22 JSON σχήματα που αφορούν τις θερμίδες που έκαψαν 3 διαφορετικα ατόμα. Εάν ο χρήστης επιθυμεί να τρέξει το πρόγραμμα χρησιμοποιώντας δικές του μετρήσεις, θα πρέπει να διαγράψει τα .JSON αρχεία που υπάρχουν μέσα στον φάκελο, και να ανεβάσει .json αρχεία που αφορούν τις δικές του μετρήσεις. Τα αρχεία που αποθηκεύονται θα πρέπει να έχουν όνομα της μορφής calories_burned_Αριθμός_ΌνομαΧρήστη.json (πχ calories_burned_1_Dimitris.json).
4. Τα δεδομένα υγείας που αφορούν τους καρδιακούς παλμούς ενός ατόμου πρέπει να αποθηκευτούν στον φάκελο "HealthDataProject/schema/Heart Rate". Απαραίτητη προυπόθεση είναι τα αρχεία αυτά να είναι JSON σχήματα σε μορφή Open mHealth. Μέσα στον φάκελο υπάρχουν ήδη 22 JSON σχήματα που αφορούν τους καρδιακούς παλμούς 3 διαφορετικών ατόμων. Εάν ο χρήστης επιθυμεί να τρέξει το πρόγραμμα χρησιμοποιώντας δικές του μετρήσεις, θα πρέπει να διαγράψει τα .JSON αρχεία που υπάρχουν μέσα στον φάκελο, και να ανεβάσει .json αρχεία που αφορούν τις δικές του μετρήσεις. Τα αρχεία που αποθηκεύονται θα πρέπει να έχουν όνομα της μορφής heart_rate_Αριθμός_ΌνομαΧρήστη.json (πχ heart_rate_1_Dimitris.json).
Τα δεδομένα υγείας που αφορούν τις δραστηριότητες που έκανε ένα ατόμο πρέπει να αποθηκευτούν στον φάκελο "HealthDataProject/schema/Physical Activity". Απαραίτητη προυπόθεση είναι τα αρχεία αυτά να είναι JSON σχήματα σε μορφή Open mHealth. Μέσα στον φάκελο υπάρχουν ήδη 22 JSON σχήματα που αφορούν τις δραστηριότητες που έκαναν 3 διαφορετικα ατόμα. Εάν ο χρήστης επιθυμεί να τρέξει το πρόγραμμα χρησιμοποιώντας δικές του μετρήσεις, θα πρέπει να διαγράψει τα .JSON αρχεία που υπάρχουν μέσα στον φάκελο, και να ανεβάσει .json αρχεία που αφορούν τις δικές του μετρήσεις. Τα αρχεία που αποθηκεύονται θα πρέπει να έχουν όνομα της μορφής calories_burned_Αριθμός_ΌνομαΧρήστη.json (πχ calories_burned_1_Dimitris.json).
