package com.example.devandroid.tp8v2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

public class ActividadPpal extends Activity {
    CCGLSurfaceView VistaPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        VistaPrincipal = new CCGLSurfaceView(this);
        setContentView(VistaPrincipal);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActividadPpal.clsJuego miGenialJuego;
        miGenialJuego = new ActividadPpal.clsJuego(VistaPrincipal);
        miGenialJuego.ComenzarJuego();
    }

    public class clsJuego {
        CCGLSurfaceView _VistaDelJuego;
        CCSize _Pantalla;
        Sprite _Objeto;
        Sprite _Objeto2;
        boolean _estaTocandoAlJugador;

        public clsJuego(CCGLSurfaceView VistaDelJuego) {
            Log.d("Comienzo", "Comienza el constructor de la clase");
            _VistaDelJuego = VistaDelJuego;
        }

        public void ComenzarJuego() {
            Log.d("Comienzo", "Comienzo el juego");
            Director.sharedDirector().attachInView(_VistaDelJuego);

            _Pantalla = Director.sharedDirector().displaySize();
            Log.d("Comienzo", "Pantalla - Ancho: " + _Pantalla.getWidth() + " - Alto " + _Pantalla.getHeight());

            Log.d("Comienzo", "Declaro e instancio la escena");
            Scene escenaAUsar;
            escenaAUsar = EscenaComienzo();

            Log.d("Comienzo", "le digo al director que inicie la escena");
            Director.sharedDirector().runWithScene(escenaAUsar);
        }

        private org.cocos2d.nodes.Scene EscenaComienzo() {
            Log.d("EscenaComienzo", "Comienza");
            Scene escenaADevolver;
            escenaADevolver = Scene.node();

            Log.d("EscenaComienzo", "Voy a agregar la capa");
            ActividadPpal.clsJuego.capaJuego unaCapa;
            unaCapa = new ActividadPpal.clsJuego.capaJuego();
            escenaADevolver.addChild(unaCapa);

            Log.d("EscenaComienzo", "Devuelvo la escena creada");
            return escenaADevolver;
        }

        class capaJuego extends Layer {
            public capaJuego() {
                Log.d("CapaJuego", "Comienza el constructor");

                Log.d("CapaJuego", "Ubico al objeto en su lugar inicial");

                ponerObjeto1();
                ponerObjeto2();

                Log.d("CapaJuego", "Habilito el touch");
                setIsTouchEnabled(true);
            }


            public void ponerObjeto1() {
                Log.d("UbicoPersonaje", "le asigno una imagen");

                _Objeto = Sprite.sprite("imagen1.png");

                Log.d("UbicoPersonaje", "le asigno su posicion inicial");
                CCPoint pocicionInicial;
                pocicionInicial = new CCPoint();

                Random generador;
                generador = new Random();
                float AnchoObjeto;
                AnchoObjeto = _Objeto.getWidth();
                pocicionInicial.x = generador.nextInt((int) (_Pantalla.getWidth() - AnchoObjeto));
                pocicionInicial.x += AnchoObjeto / 2;

                float AltoObjeto;
                AltoObjeto = _Objeto.getHeight();
                pocicionInicial.y = generador.nextInt((int) (_Pantalla.getHeight() - AltoObjeto));
                pocicionInicial.y += AltoObjeto / 2;

                Log.d("UbicoPersonaje", "Los ubico");
                _Objeto.setPosition(pocicionInicial.x, pocicionInicial.y);

                super.addChild(_Objeto);

            }

            public void ponerObjeto2() {
                Log.d("UbicoPersonaje", "le asigno una imagen");

                _Objeto2 = Sprite.sprite("imagen2.png");

                Log.d("UbicoPersonaje", "le asigno su posicion inicial");
                CCPoint pocicionInicial;
                pocicionInicial = new CCPoint();

                Random generador;
                generador = new Random();
                float AnchoObjeto;
                AnchoObjeto = _Objeto2.getWidth();
                pocicionInicial.x = generador.nextInt((int) (_Pantalla.getWidth() - AnchoObjeto));
                pocicionInicial.x += AnchoObjeto / 2;

                float AltoObjeto;
                AltoObjeto = _Objeto2.getHeight();
                pocicionInicial.y = generador.nextInt((int) (_Pantalla.getHeight() - AltoObjeto));
                pocicionInicial.y += AltoObjeto / 2;

                Log.d("UbicoPersonaje", "Los ubico");
                _Objeto2.setPosition(pocicionInicial.x, pocicionInicial.y);

                super.addChild(_Objeto2);

            }
            @Override
            public boolean ccTouchesBegan(MotionEvent event) {
                CCPoint PuntoTocado;
                PuntoTocado = new CCPoint();

                PuntoTocado.x = event.getX();
                PuntoTocado.y = _Pantalla.getHeight() - event.getY();
                Log.d("ControlDeToque", "Comienza el toque en X:" + PuntoTocado.x + " - Y: " + PuntoTocado.y);
                if (InterseccionEntrePuntoySprite(_Objeto, PuntoTocado.x, PuntoTocado.y)) {
                    Log.d("MoverObjeto","Entre al if");
                    moverObjeto(PuntoTocado);
                    _estaTocandoAlJugador = true;
                } else {
                    Log.d("MoverObjeto","Entre al if2");
                    _estaTocandoAlJugador = false;
                }
                return true;
            }

            @Override
            public boolean ccTouchesMoved(MotionEvent event) {
                CCPoint PuntoTocado;
                PuntoTocado = new CCPoint();

                PuntoTocado.x = event.getX();
                PuntoTocado.y = _Pantalla.getHeight() - event.getY();
                Log.d("ControlDeToque", "Mueve el toque X:" + PuntoTocado.x + " - Y: " + PuntoTocado.y);
                if (_estaTocandoAlJugador) {
                    Log.d("MoverObjeto","Entre al if3");
                    moverObjeto(PuntoTocado);
                }
                return true;
            }
        }



        void moverObjeto(CCPoint PuntoAMover) {
            Log.d("MoverObjeto", "Muevo el objeto");
            _Objeto.setPosition(PuntoAMover.x, PuntoAMover.y);
        }

        public boolean InterseccionEntrePuntoySprite(Sprite SpriteAVerificar, Float puntoXAVerificar, Float puntoYAVerificar) {
            Boolean HayInterseccion = false;
            //Determino los bordes de cada Sprite
            Float SpArriba, SpAbajo, SpDerecha, SpIzquierda;
            SpArriba = SpriteAVerificar.getPositionY() + SpriteAVerificar.getHeight() / 2;
            SpAbajo = SpriteAVerificar.getPositionY() - SpriteAVerificar.getHeight() / 2;
            SpDerecha = SpriteAVerificar.getPositionX() + SpriteAVerificar.getWidth() / 2;
            SpIzquierda = SpriteAVerificar.getPositionX() - SpriteAVerificar.getWidth() / 2;

            if(puntoXAVerificar>=SpIzquierda && puntoXAVerificar<=SpDerecha && puntoYAVerificar>=SpAbajo && puntoYAVerificar<=SpArriba){
                HayInterseccion = true;
            }
            return HayInterseccion;
        }
        public boolean InterseccionEntrePuntoySprite2(Sprite SpriteAVerificar, Float puntoXAVerificar, Float puntoYAVerificar) {
            Boolean HayInterseccion = false;
            //Determino los bordes de cada Sprite
            Float SpArriba, SpAbajo, SpDerecha, SpIzquierda;
            SpArriba = SpriteAVerificar.getPositionY() + SpriteAVerificar.getHeight() / 2;
            SpAbajo = SpriteAVerificar.getPositionY() - SpriteAVerificar.getHeight() / 2;
            SpDerecha = SpriteAVerificar.getPositionX() + SpriteAVerificar.getWidth() / 2;
            SpIzquierda = SpriteAVerificar.getPositionX() - SpriteAVerificar.getWidth() / 2;

            if(puntoXAVerificar>=SpIzquierda && puntoXAVerificar<=SpDerecha && puntoYAVerificar>=SpAbajo && puntoYAVerificar<=SpArriba){
                HayInterseccion = true;
            }
            return HayInterseccion;
        }


    }
}
