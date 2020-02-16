package com.coutinsociety.kanmaServer.kanmaDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.database.Cursor.FIELD_TYPE_FLOAT;
import static android.database.Cursor.FIELD_TYPE_INTEGER;

public class KanmaDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Kanma.db";

    public static final String ABONNEMENT = "ABONNEMENT",
        ID_ABONNEMENT = "id_abonnement",
        ABO_ASSOCIATION = "abo_association",
        ABO_ETUDIANT = "abo_etudiant";

    public static final String ADHERER = "ADHERER",
            id_user = "id_user",
            id_groupe = "id_groupe";

    public static final String BILLET = "BILLET",
            id_billet = "id_billet",
            codeBarre = "code_barre",
            id_evenement = "id_evenement";

    public static final String CALENDRIER = "CALENDRIER",
            id_calendrier = "id_calendrier",
            date = "date",
            heure = "heure";

    public static final String CLASSEMENT = "CLASSEMENT",
            id_classement = "id_classement",
            resultat = "resultat",
            periodicite = "periodicite";

    public static final String CONSULTER = "CONSULTER";

    public static final String CONVERSATION = "CONVERSATION",
            id_conversation = "id_conversation",
            titre = "titre";

    public static final String DIRIGER = "DIRIGER";

    public static final String ENVOYER = "ENVOYER";

    public static final String EVENEMENT = "EVENEMENT";
    public static final String nom_evenement = "nom_evenement";
    public static final String date_evenement = "date_evenement";
    public static final String lieu_evenemenet = "lieu_evenemenet";
    public static final String nb_participant = "nb_participant";
    public static final String nb_avis_positif = "nb_avis_positif";
    public static final String longitude = "longitude";
    public static final String latitude = "latitude";
    public static final String id_type_evenement = "id_type_evenement";

    public static final String GAGNER = "GAGNER";
    public static final String id_jeton = "id_jeton";

    public static final String GROUPE = "GROUPE";
    public static final String nom = "nom";
    public static final String logo = "logo";
    public static final String description = "description";
    public static final String date_adhesion = "date_adhesion";


    public static final String JETON = "JETON";

    public static final String MESSAGE = "MESSAGE";
    public static final String id_message = "id_message";
    public static final String contenu = "contenu";
    public static final String date_message = "date_message";
    public static final String heure_message = "heure_message";

    public static final String PAIEMENT = "PAIEMENT";
    public static final String id_paiement = "id_paiement";
    public static final String numero_carte = "numero_carte";
    public static final String date_expiration = "date_expiration";
    public static final String cryptogramme = "cryptogramme";

    public static final String PARRAINAGE = "PARRAINAGE";
    public static final String id_parrainage = "id_parrainage";

    public static final String PARTICIPER = "PARTICIPER";

    public static final String POSSEDER = "POSSEDER";

    public static final String RANG = "RANG";
    public static final String id_rang = "id_rang";
    public static final String droit = "droit";

    public static final String RECEVOIR = "RECEVOIR";

    public static final String STATUT = "STATUT";
    public static final String id_statut = "id_statut";
    public static final String type_statut = "type_statut";

    public static final String THEME = "THEME";
    public static final String id_theme = "id_theme";

    public static final String TYPE_EVENEMENT = "TYPE_EVENEMENT";
    public static final String type_evenement = "type_evenement";

    public static final String TYPE_USER = "TYPE_USER";
    public static final String id_type_user = "id_type_user";
    public static final String type = "type";

    public static final String UTILISATEUR = "Utilisateur";
    public static final String prenom = "prenom";
    public static final String age = "age";
    public static final String blason = "blason";
    public static final String photo_prof = "photo_prof";
    public static final String login = "login";
    public static final String mdp = "mdp";
    public static final String email = "email";
    public static final String sexe = "sexe";
    public static final String nationalite = "nationalite";
    public static final String qte_jetons = "qte_jetons";
    public static final String nb_evenement_cree = "nb_evenement_cree";
    public static final String nb_evenement_participe = "nb_evenement_participe";
    public static final String drapeau = "drapeau";
    public static final String date_inscription = "date_inscription";
    public static final String date_derniere_connexion = "date_derniere_connexion";

    public KanmaDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase maBase) {

        maBase.execSQL(" create table " + ABONNEMENT + " ( " +
                ID_ABONNEMENT + " integer not null primary key autoincrement," +
                ABO_ASSOCIATION + " boolean," +
                ABO_ETUDIANT + " boolean ) ");


        maBase.execSQL(" create table " + BILLET + " ( " +
                id_billet + " integer not null primary key autoincrement," +
                codeBarre + " text," +
                id_user + " integer," +
                id_evenement + " integer ) ");

        maBase.execSQL( "create table " + CALENDRIER + "(" +
                id_calendrier + " integer not null primary key autoincrement," +
                date + " text default null," +
                heure + " text default null," +
                id_evenement + " integer default null ) ");

        maBase.execSQL( "create table " + CLASSEMENT + "(" +
                id_classement + " integer not null primary key autoincrement," +
                resultat + " double default null," +
                periodicite + " text default null ) ");

        maBase.execSQL( "create table " + CONSULTER + "(" +
                id_user + " integer not null primary key autoincrement," +
                id_evenement + " integer default null ) ");

        maBase.execSQL( "create table " + CONVERSATION + "(" +
                id_conversation + " integer not null primary key autoincrement," +
                titre + " text default null ) ");

        maBase.execSQL( "create table " + DIRIGER + "(" +
                id_user + " integer not null primary key autoincrement," +
                id_groupe + " integer default null ) ");

        maBase.execSQL( "create table " + ENVOYER + "(" +
                id_user + " integer not null primary key autoincrement," +
                id_message + " integer default null ) ");

        maBase.execSQL( "create table " + GAGNER + "(" +
                id_user + " integer not null primary key autoincrement," +
                id_jeton + " integer default null ) ");


        maBase.execSQL( "create table " + JETON + "(" +
                id_jeton + " integer not null primary key autoincrement," +
                nom + " text default null ) ");

        maBase.execSQL( "create table " + MESSAGE + "(" +
                id_message + " integer not null primary key autoincrement," +
                contenu + " text not null," +
                date_message + " text default null," +
                heure_message + " text default null," +
                id_conversation + " integer default null ) ");

        maBase.execSQL( "create table " + PAIEMENT + "(" +
                id_paiement + " integer not null primary key autoincrement," +
                numero_carte + " integer default null," +
                date_expiration + " text default null," +
                nom + " text default null," +
                cryptogramme + " integer default null ) ");

        maBase.execSQL( "create table " + PARRAINAGE + "(" +
                id_parrainage + " integer not null primary key autoincrement," +
                date + " text default null," +
                id_user + " integer default null ) ");

        maBase.execSQL( "create table " + POSSEDER + "(" +
                id_user + " integer not null primary key autoincrement," +
                id_conversation + " integer default null," +
                id_groupe + " integer default null ) ");

        maBase.execSQL( "create table " + RANG + "(" +
                id_rang+ " integer not null primary key autoincrement," +
                droit + " text default null," +
                id_user + " integer default null ) ");

        maBase.execSQL( "create table " + RECEVOIR + "(" +
                id_user + " integer not null primary key autoincrement," +
                id_message + " integer default null ) ");

        maBase.execSQL( "create table " + STATUT + "(" +
                id_statut + " integer not null primary key autoincrement," +
                type_statut + " text default null," +
                id_user + " integer default null ) ");

        maBase.execSQL( "create table " + THEME + "(" +
                id_theme + " integer not null primary key autoincrement," +
                nom + " text default null," +
                id_user + " integer default null ) ");

        maBase.execSQL( "create table " + TYPE_EVENEMENT + "(" +
                id_type_evenement + " integer not null primary key autoincrement," +
                type_evenement + " text default null ) ");

        maBase.execSQL( "create table " + TYPE_USER + "(" +
                id_type_user + " integer not null primary key autoincrement," +
                type + " text default null ) ");

        maBase.execSQL( "create table " + UTILISATEUR + "(" +
                id_user + " integer not null primary key autoincrement," +
                nom + " text default null," +
                prenom + " text default null," +
                age + " double default null," +
                description + " text default null," +
                blason + " text default null," +
                photo_prof + " text default null," +
                login + " text default null," +
                mdp + " text default null," +
                email + " text default null," +
                sexe + " text default null," +
                nationalite + " text default null," +
                qte_jetons + " double default null," +
                nb_evenement_cree + " double default null," +
                nb_evenement_participe + " double default null," +
                drapeau + " integer default null," +
                date_inscription + " text default null," +
                date_derniere_connexion + " text default null," +
                id_type_user + " integer default null," +
                id_theme + " integer default null," +
                id_statut + " integer default null," +
                ID_ABONNEMENT + " integer default null," +
                id_calendrier + " integer default null )");

        maBase.execSQL( "create table " + GROUPE + "(" +
                id_groupe + " integer not null primary key autoincrement," +
                nom + " text default null," +
                logo + " text default null," +
                description + " text default null," +
                date_adhesion + " text default null," +
                id_user + " integer default null," +
                id_classement + " integer default null, " +
                "FOREIGN KEY ("+id_user+") REFERENCES "+ UTILISATEUR +" ("+ id_user+ "))");

        maBase.execSQL( "create table " + EVENEMENT + "(" +
                id_evenement + " integer not null primary key autoincrement," +
                nom_evenement + " text default null," +
                description + " text default null," +
                logo + " byte default null," +
                date_evenement + " text default null," +
                lieu_evenemenet + " text default null," +
                nb_participant + " integer default null," +
                nb_avis_positif + " integer default null," +
                longitude + " text default null," +
                latitude + " text default null," +
                id_user + " integer default null," +
                id_type_evenement + " integer default null," +
                id_groupe + " integer default null," +
                 "FOREIGN KEY ("+id_user+") REFERENCES "+ UTILISATEUR +" ("+ id_user + ")," +
                "FOREIGN KEY ("+id_user+") REFERENCES "+ GROUPE +" ("+ id_groupe+ "))");

        maBase.execSQL(" create table " + ADHERER + " ( " +
                id_user + " integer not null," +
                id_groupe + " integer not null," +
                "PRIMARY KEY (" + id_user + " ," + id_groupe + ") ," +
                "FOREIGN KEY ("+id_user+") REFERENCES "+ UTILISATEUR +" ("+ id_user + ")," +
                "FOREIGN KEY ("+id_user+") REFERENCES "+ GROUPE +" ("+ id_groupe+ "))");

        maBase.execSQL( "create table " + PARTICIPER + "(" +
                id_user + " integer not null primary key autoincrement," +
                id_evenement + " integer default null ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase maBase, int oldVersion, int newVersion) {

    maBase.execSQL(" drop table if exists " + ABONNEMENT);
    maBase.execSQL(" drop table if exists " + ADHERER);
    maBase.execSQL(" drop table if exists " + BILLET);
    maBase.execSQL(" drop table if exists " + CALENDRIER);
    maBase.execSQL(" drop table if exists " + CLASSEMENT);
    maBase.execSQL(" drop table if exists " + CONSULTER);
    maBase.execSQL(" drop table if exists " + CONVERSATION);
    maBase.execSQL(" drop table if exists " + DIRIGER);
    maBase.execSQL(" drop table if exists " + ENVOYER);
    maBase.execSQL(" drop table if exists " + EVENEMENT);
    maBase.execSQL(" drop table if exists " + GAGNER);
    maBase.execSQL(" drop table if exists " + GROUPE);
    maBase.execSQL(" drop table if exists " + JETON);
    maBase.execSQL(" drop table if exists " + MESSAGE);
    maBase.execSQL(" drop table if exists " + PAIEMENT);
    maBase.execSQL(" drop table if exists " + PARRAINAGE);
    maBase.execSQL(" drop table if exists " + PARTICIPER);
    maBase.execSQL(" drop table if exists " + POSSEDER);
    maBase.execSQL(" drop table if exists " + RANG);
    maBase.execSQL(" drop table if exists " + RECEVOIR);
    maBase.execSQL(" drop table if exists " + STATUT);
    maBase.execSQL(" drop table if exists " + THEME);
    maBase.execSQL(" drop table if exists " + TYPE_EVENEMENT);
    maBase.execSQL(" drop table if exists " + TYPE_USER);
    maBase.execSQL(" drop table if exists " + UTILISATEUR);

    onCreate(maBase);

    }

    //REQUEST TO JSON
    private JSONArray executeRequest(String request) {
        Log.d("BD:executeRequest", request );
        SQLiteDatabase maBase=this.getReadableDatabase();
        Cursor cursor;
        cursor=maBase.rawQuery(request,null);
        JSONArray rappels=new JSONArray();
        //String[] rappels=new String[res.getCount()];
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ ){
                try {
                    if( cursor.getString(i) != null &&cursor.getColumnName(i) != null )
                    {
                        Log.d("BD:executeRequest", "value "+cursor.getString(i) );
                        switch (cursor.getType(i)){
                            case FIELD_TYPE_INTEGER:
                                rowObject.put(cursor.getColumnName(i) ,cursor.getInt(i) );
                                break;
                            case FIELD_TYPE_FLOAT:
                                rowObject.put(cursor.getColumnName(i),cursor.getFloat(i));
                                break;

                                default:rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                break;
                        }
                    }
                    else
                        Log.d("BD:executeRequest", "value NULL");
                }catch(Exception e){ Log.d("BD:executeRequest", e.getMessage()  );}
            }
            rappels.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("BD:executeRequest", rappels.toString() );
        return rappels;
    }

    //Insert to id
    private int executeInsert(String table,ContentValues values){
        SQLiteDatabase maBase=this.getReadableDatabase();
        return (int)maBase.insert(table, null, values);
    }

    //Update to boolean
    private boolean executeUpdate(String table,ContentValues values,String whereClause,String[] whereValues){
        Log.d("BD:update",values.toString());
        SQLiteDatabase maBase=this.getReadableDatabase();
        return maBase.update(table, values, whereClause, whereValues)!=-1;
    }

    //Delete to boolean
    private boolean executeDelete(String table,String whereClause,String[] whereValues){
        SQLiteDatabase maBase=this.getReadableDatabase();
        return maBase.delete(table, whereClause, whereValues)!=-1;
    }


    public JSONArray listeAbonnement(){

        String request="SELECT * FROM "+ ABONNEMENT;

        return executeRequest(request);
    }

    public JSONArray listeAdherer(){

        String request="SELECT * FROM "+ ADHERER;

        return executeRequest(request);
    }

    public JSONArray listeBillet(){

        String request="SELECT * FROM "+ BILLET;

        return executeRequest(request);
    }

    public JSONArray listeCalendrier(){

        String request="SELECT * FROM "+ CALENDRIER;

        return executeRequest(request);
    }

    public JSONArray listeUtilisateur(){

        String request="SELECT * FROM "+ UTILISATEUR;

        return executeRequest(request);
    }

    public JSONArray listeGroup(){

        String request="SELECT * FROM "+ GROUPE;

        return executeRequest(request);
    }

    public JSONArray listeEvent(){

        String request="SELECT * FROM "+ EVENEMENT;

        return executeRequest(request);
    }

    public JSONArray listeParticiper(){

        String request="SELECT * FROM "+ PARTICIPER;

        return executeRequest(request);
    }

    //Inscription
    public int createUser(JSONObject jsonObject) throws JSONException {
        JSONObject jsvalue=jsonObject.getJSONObject("createUser");

        ContentValues content = new ContentValues();

        content.put(KanmaDB.login,jsvalue.getString(KanmaDB.login));
        content.put(KanmaDB.mdp,jsvalue.getString(KanmaDB.mdp));
        content.put(KanmaDB.nom,jsvalue.getString(KanmaDB.nom));
        content.put(KanmaDB.prenom,jsvalue.getString(KanmaDB.prenom));
        content.put(KanmaDB.sexe,jsvalue.getString(KanmaDB.sexe));

        return executeInsert(UTILISATEUR, content);
    }
    //Delete User
    public boolean deleteUser(JSONObject jsonObject) throws JSONException {

        JSONObject jsvalue=jsonObject.getJSONObject("deleteUser");

        int whereValue = jsvalue.getInt(id_user);

        executeDelete(PARTICIPER, " id_user = ? ", new String[]{Integer.toString(whereValue)});
        executeDelete(ADHERER, " id_user = ? ", new String[]{Integer.toString(whereValue)});
        return executeDelete(UTILISATEUR, " id_user = ? ", new String[]{Integer.toString(whereValue)});

    }

    //Connexion
    public JSONArray findUserWithLogin(JSONObject jsonObject) throws JSONException {

        JSONObject jsvalue=jsonObject.getJSONObject("findUserWithLogin");

        if(!(jsvalue.has("username")||jsvalue.has("password")))return null;
        String username = jsvalue.getString("username");
        String password = jsvalue.getString("password");

        String request1 = "SELECT "+ id_user + " , " + nom + " , " + prenom +  " , " + login +  " , " + sexe +  " , " + description + " , " + photo_prof +  " FROM " + UTILISATEUR + " WHERE " + login + " = '" + username+"'  AND " + mdp + " = '" + password+"'";

        JSONArray result=executeRequest(request1);
        if(result.length()==0)return null;

        //FIXME
        /*String request2="SELECT "+id_groupe+" WHERE "+id_user+" = "+result.getJSONObject(0).getInt(id_user);
        JSONArray result2=executeRequest(request2);
        if(request2.length()!=0){
            result.getJSONObject(0).put(id_groupe,result2);

        }*/
        return result;

    }

    //Retrouver un user par le nom
    public JSONArray getUserBeginWith(JSONObject jsonObject) throws JSONException {
        String begining = jsonObject.getString("getUserBeginWith");

        String request = "SELECT "+ id_user+ " , " + nom + " , " + prenom + " , " + description + " , " + photo_prof +","+ sexe+ " FROM " + UTILISATEUR + " WHERE " + prenom + " LIKE '" + begining+"%'" ;

        return executeRequest(request);
    }

    //modifier/ajouter un photo
    public boolean addPhotoForUser(JSONObject jsonObject) throws JSONException {

        JSONObject jsvalue=jsonObject.getJSONObject("addPhotoForUser");
        ContentValues content = new ContentValues();

        content.put(KanmaDB.photo_prof,jsvalue.getString(KanmaDB.photo_prof));
        int whereValue = jsvalue.getInt(id_user);

        return executeUpdate(UTILISATEUR, content, " id_user = ? ", new String[]{Integer.toString(whereValue)});
    }

    //modifier/ajouter une description
    public boolean addDescriptionForUser(JSONObject jsonObject) throws JSONException {

        JSONObject jsvalue=jsonObject.getJSONObject("addDescriptionForUser");

        ContentValues content = new ContentValues();

        content.put(KanmaDB.description, jsvalue.getString(KanmaDB.description));
        int whereValue = jsvalue.getInt(id_user);

        return executeUpdate(UTILISATEUR, content, " id_user = ? ", new String[]{Integer.toString(whereValue)});
    }

    //TODO
    //modifier tout autre element
    public boolean modifUser(JSONObject jsonObject) throws JSONException {

        JSONObject jsvalue=jsonObject.getJSONObject("modifUser");

        ContentValues content = new ContentValues();

        if(jsvalue.has(KanmaDB.prenom))content.put(KanmaDB.prenom, jsvalue.getString(KanmaDB.prenom));
        if(jsvalue.has(KanmaDB.nom))content.put(KanmaDB.nom, jsvalue.getString(KanmaDB.nom));
        if(jsvalue.has(KanmaDB.login))content.put(KanmaDB.login, jsvalue.getString(KanmaDB.login));
        if(jsvalue.has(KanmaDB.mdp))content.put(KanmaDB.mdp, jsvalue.getString(KanmaDB.mdp));

        int whereValue = jsvalue.getInt(id_user);

        return executeUpdate(UTILISATEUR, content, " id_user = ? ", new String[]{Integer.toString(whereValue)});
    }

    //Recherche d'utilisateur avec id
    public JSONArray findUserWithId(JSONObject jsonObject) throws JSONException {

        JSONObject jsvalue=jsonObject.getJSONObject("findUserWithId");

        String id_users = jsvalue.getString("id_users");

        String request = "SELECT "+ id_user+ " , " + nom + " , " + prenom + " , " + description + " , " + photo_prof + " FROM " + UTILISATEUR + " WHERE " + id_user + " = " + id_users;
Log.d("BD",request);
        return executeRequest(request);

    }

    //Creer un groupe
    public int createGroup(JSONObject jsonObject) throws JSONException{

        JSONObject jsvalue=jsonObject.getJSONObject("createGroup");

        JSONArray jsvaluearray = jsvalue.getJSONArray("id_users");

        ContentValues content = new ContentValues();

        content.put(KanmaDB.nom,jsvalue.getString(KanmaDB.nom));
        if(jsvalue.has(KanmaDB.description))
            content.put(KanmaDB.description,jsvalue.getString(KanmaDB.description));
        if(jsvalue.has(KanmaDB.logo))
            content.put(KanmaDB.logo,jsvalue.getString(KanmaDB.logo));

        int result = executeInsert(GROUPE, content);
        insertUsersInGroup(result, jsvaluearray);
        return result;
    }

    //Recherche groupe avec id
    public JSONArray findGroupWithId(JSONObject jsonObject) throws JSONException {

        JSONObject jsvalue=jsonObject.getJSONObject("findGroupWithId");

        String id_groupes = jsvalue.getString(id_groupe);

        String request = "SELECT "+ id_groupe+ " , " + nom + " , " + prenom + " , " + description + " , " + photo_prof + " FROM " + GROUPE + " WHERE " + id_groupe + " = " + id_groupes;

        //TODO add participent+admin
        return executeRequest(request);

    }

    //Rejoindre un groupe 1/+ User(s)
    public boolean addUserForGroup(JSONObject jsonObject) throws JSONException {
        JSONObject jsvalue=jsonObject.getJSONObject("addUserForGroup");
        JSONArray jsvaluearray = jsvalue.getJSONArray("id_users");

        return insertUsersInGroup(jsvalue.getInt(id_groupe),jsvaluearray);
    }
    private boolean insertUsersInGroup(int id,JSONArray jsvaluearray) throws JSONException {

        for (int i=0; i<jsvaluearray.length(); i++){
            ContentValues content = new ContentValues();
            content.put(id_groupe,id);
            content.put(id_user,jsvaluearray.getInt(i));

            if(executeInsert(ADHERER,content)==-1)
                return false;
        }
        return true;
    }


    public JSONArray findUserInAdherent(JSONObject jsonObject) throws JSONException {
        String id_groupes = jsonObject.getString("findUserInAdherent");

        String request = "SELECT "+ id_user+" FROM " + ADHERER + " WHERE " + id_groupe + " =" + id_groupes;

        return executeRequest(request);
    }

    public JSONArray getGroupBeginWith(JSONObject jsonObject) throws JSONException {
        String begining = jsonObject.getString("getGroupBeginWith");

        String request = "SELECT "+ id_groupe+ " , " + nom + " , " + description + " , " + logo + " FROM " + GROUPE + " WHERE " + nom + " LIKE '" + begining+"%'" ;
//TODO add participent+admin
        return executeRequest(request);
    }

    //modifier/ajouter un photo
    public boolean addPhotoForGroup(JSONObject jsonObject) throws JSONException {
        JSONObject jsvalue=jsonObject.getJSONObject("addPhotoForGroup");

        ContentValues content = new ContentValues();

        content.put(KanmaDB.logo,jsvalue.getString(KanmaDB.logo));

        int whereValue = jsvalue.getInt(id_groupe);

        return executeUpdate(GROUPE, content, " id_groupe = ? ", new String[]{Integer.toString(whereValue)});
    }

    //modifier/ajouter une description
    public boolean addDescriptionForGroup(JSONObject jsonObject) throws JSONException {
        JSONObject jsvalue=jsonObject.getJSONObject("addDescriptionForGroup");

        ContentValues content = new ContentValues();

        content.put(KanmaDB.description, jsvalue.getString(KanmaDB.description));

        int whereValue = jsvalue.getInt(id_groupe);
        return executeUpdate(GROUPE, content, " id_groupe = ? ", new String[]{Integer.toString(whereValue)});
    }

//Creer un evenement
    public int createEvent(JSONObject jsonObject) throws JSONException {
        JSONObject jsvalue=jsonObject.getJSONObject("createEvent");

        ContentValues content = new ContentValues();

        content.put(KanmaDB.nom_evenement,jsvalue.getString(KanmaDB.nom_evenement));
        if(jsvalue.has(KanmaDB.lieu_evenemenet))
            content.put(KanmaDB.lieu_evenemenet,jsvalue.getString(KanmaDB.lieu_evenemenet));
        content.put(KanmaDB.latitude,jsvalue.getDouble(KanmaDB.latitude));
        content.put(KanmaDB.longitude,jsvalue.getDouble(KanmaDB.longitude));
        content.put(KanmaDB.date_evenement,jsvalue.getLong(KanmaDB.date_evenement));
        if (jsvalue.has(KanmaDB.logo))content.put(KanmaDB.logo,jsvalue.getString(KanmaDB.logo));
        if (jsvalue.has(KanmaDB.description))content.put(KanmaDB.description,jsvalue.getString(KanmaDB.description));

        return executeInsert(EVENEMENT, content);
    }

    //Recherche event avec id
    public JSONArray findEventWithId(JSONObject jsonObject) throws JSONException {

        JSONObject jsvalue=jsonObject.getJSONObject("findEventWithId");

        String id_event = jsvalue.getString("id_event");

        String request = "SELECT "+ id_evenement+ " , " + nom_evenement + " , " + description + " , " + logo + " , " + date_evenement + " , " + lieu_evenemenet + " , " +latitude+ " , " +longitude+ " FROM " + EVENEMENT + " WHERE " + id_evenement + " = " + id_event;
//TODO add participent+admin
        return executeRequest(request);

    }

    public JSONArray findUserInParticipant(JSONObject jsonObject) throws JSONException {
        String id_event = jsonObject.getString("findUserInParticipant");

        String request = "SELECT "+ id_user + " FROM " + PARTICIPER + " WHERE " + id_evenement + " = " + id_event;

        return executeRequest(request);
    }

    //Rejoindre un event 1/+ User(s)
    public boolean rejoindreEvenement(JSONObject jsonObject) throws JSONException {
        SQLiteDatabase maBase = this.getWritableDatabase();

        JSONObject jsvalue=jsonObject.getJSONObject("rejoindreEvenement");
        //TODO should be const id_user
        JSONArray jsvaluearray = jsvalue.getJSONArray("id_users");

        return insertUsersInEvent(jsvalue.getInt(id_evenement),jsvaluearray);
    }
    private boolean insertUsersInEvent(int id,JSONArray jsvaluearray) throws JSONException {

        for (int i=0; i<jsvaluearray.length(); i++){
            ContentValues content = new ContentValues();
            content.put(id_user,jsvaluearray.getInt(i));
            content.put(id_evenement,id);

            if (executeInsert(PARTICIPER,content) == -1)
                return false;
        }
        return true;
    }

    //Retrouver un evenement par le nom
    public JSONArray getEventBeginWith(JSONObject jsonObject) throws JSONException {
        String begining = jsonObject.getString("getEventBeginWith");

        String request = "SELECT "+ id_evenement+ " , " + nom_evenement + " , " + description + " , " + logo + " , " + date_evenement + " , " + lieu_evenemenet + " , " +latitude+ " , " +longitude+ " FROM " + EVENEMENT + " WHERE " + nom_evenement + " LIKE '" + begining+"%'" ;

        return executeRequest(request);
    }

    //ajouter/modif photo
    public boolean addPhotoForEvent(JSONObject jsonObject) throws JSONException {
        JSONObject jsvalue=jsonObject.getJSONObject("addPhotoForEvent");

        ContentValues content = new ContentValues();

        content.put(KanmaDB.logo,jsvalue.getString(KanmaDB.logo));

        int whereValue = jsvalue.getInt(id_evenement);
        return executeUpdate(EVENEMENT, content, " id_evenement = ? ", new String[]{Integer.toString(whereValue)});
    }

    //ajouter/modif desc
    public boolean addDescriptionForEvent(JSONObject jsonObject) throws JSONException {
        JSONObject jsvalue=jsonObject.getJSONObject("addDescriptionForEvent");
        ContentValues content = new ContentValues();

        content.put(KanmaDB.description, jsvalue.getString(KanmaDB.description));

        int whereValue = jsvalue.getInt(id_evenement);
        return executeUpdate(EVENEMENT, content, " id_evenement = ? ", new String[]{Integer.toString(whereValue)});
    }

}
