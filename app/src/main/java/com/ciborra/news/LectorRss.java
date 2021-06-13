package com.ciborra.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.ciborra.news.MainActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class LectorRss extends AsyncTask<Void, Void, Void> {
    Context context;
    MainActivity mainActivity;

    public String direccion;
    URL url;
    RecyclerView recyclerView;
    ArrayList<noticia> noticias;
    // ProgressDialog progressDialog;
    public LectorRss(Context context, RecyclerView recyclerView){
        this.recyclerView= recyclerView;
        this.context=context;
        //progressDialog = new ProgressDialog(context);
        //progressDialog.setMessage("Cargando...");
    }
    @Override
    protected void onPreExecute() {
       // progressDialog.show();
        super.onPreExecute();

        if(mainActivity.as != null){
            direccion = mainActivity.as;
        }else{
            //direccion = "https://www.sport.es/es/rss/last-news/news.xml";
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        AdapterNoticia adapterNoticia = new AdapterNoticia(noticias,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapterNoticia);
    }
    @Override
    protected Void doInBackground(Void... voids) {
    procesarXML(obtenerDatos());
        return null;
    }
    private void procesarXML(Document data){
        if (data!= null){
            noticias= new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for(int i =0; i< items.getLength(); i++){
                Node HijoActual = items.item(i);
                if(HijoActual.getNodeName().equalsIgnoreCase("item")||HijoActual.getNodeName().equalsIgnoreCase("NewsComponent")){
                    noticia Noticia = new noticia();
                    NodeList itemsChild= HijoActual.getChildNodes();
                    for(int j = 0; j<itemsChild.getLength(); j++){
                        Node actual = itemsChild.item(j);
                        if (actual.getNodeName().equalsIgnoreCase("title")){
                            Noticia.setmTitulo(actual.getTextContent());
                        }else if(actual.getNodeName().equalsIgnoreCase("link")){
                            Noticia.setmEnlace(actual.getTextContent());
                        }else if(actual.getNodeName().equalsIgnoreCase("description")){
                            Noticia.setmDescripcion(actual.getTextContent());
                        }else if(actual.getNodeName().equalsIgnoreCase("enclosure ")){
                            String mUrl = actual.getAttributes().item(0).getTextContent();
                            Noticia.setmImagen(mUrl);
                        }
                        else if(actual.getNodeName().equalsIgnoreCase("media:content")){
                            String mUrl = actual.getAttributes().item(0).getTextContent();
                            Noticia.setmImagen(mUrl);
                        }else if(actual.getNodeName().equalsIgnoreCase("enclosure url")){
                            String mUrl = actual.getAttributes().item(0).getTextContent();
                            Noticia.setmImagen(mUrl);
                        }
                        else if(actual.getNodeName().equalsIgnoreCase("pubDate")){
                            Noticia.setmFecha(actual.getTextContent());
                        }else if(actual.getNodeName().equalsIgnoreCase("dc:created")){
                            Noticia.setmFecha(actual.getTextContent());
                        }
                    }
                    noticias.add(Noticia);
                }
            }
        }
    }
    public Document obtenerDatos(){
        try{
            url = new URL(direccion);
            HttpURLConnection connection= (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc=builder.parse(inputStream);
            return xmlDoc;
        }catch (Exception e){
            e.printStackTrace();
            return null;

        }
    }


}
