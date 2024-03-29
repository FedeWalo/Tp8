package com.example.devandroid.tp8v2;

import android.app.Activity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

import android.os.Bundle;

public class ActividadPrincipal extends Activity {

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
        clsJuego miGenialJuego;
        miGenialJuego = new clsJuego(VistaPrincipal);
        miGenialJuego.ComenzarJuego();
    }

    public class clsJuego {
        CCGLSurfaceView _VistaDelJuego;
        CCSize _Pantalla;
        Sprite _Objeto;
        Sprite _Objeto2;
        boolean _estaTocandoAlJugador1=false;
        boolean _estaTocandoAlJugador2=false;

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
            capaJuego unaCapa;
            unaCapa = new capaJuego();
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
                if (InterseccionEntrePuntoySprite1(_Objeto, PuntoTocado.x, PuntoTocado.y)) {
                    Log.d("ControlDeToque", "Toco al 1");
                    moverObjeto(PuntoTocado,_Objeto);
                    _estaTocandoAlJugador1 = true;
                }
                else if(InterseccionEntrePuntoySprite2(_Objeto2,PuntoTocado.x,PuntoTocado.y)){
                    Log.d("ControlDeToque", "Toco al 2");
                    moverObjeto(PuntoTocado,_Objeto2);
                    _estaTocandoAlJugador2 = true;
                }
                return true;
            }

            @Override
            public boolean ccTouchesMoved(MotionEvent event) {
                CCPoint PuntoTocado;
                PuntoTocado = new CCPoint();

                PuntoTocado.x = event.getX();
                PuntoTocado.y = _Pantalla.getHeight() - event.getY();

                if (_estaTocandoAlJugador1) {
                    moverObjeto(PuntoTocado,_Objeto);
                    Log.d("ControlDeToque", "TOCO EL OBJ1" + PuntoTocado.x + " - Y: " + PuntoTocado.y);
                }
                else if(_estaTocandoAlJugador2){
                    moverObjeto(PuntoTocado,_Objeto2);
                    Log.d("ControlDeToque", "TOCO EL OBJ2" + PuntoTocado.x + " - Y: " + PuntoTocado.y);
                }
                return true;
            }
        }



        void moverObjeto(CCPoint PuntoAMover, Sprite ObjetoAMover) {
            Log.d("MoverObjeto", "Muevo el objeto");
            ObjetoAMover.setPosition(PuntoAMover.x, PuntoAMover.y);
        }

        public boolean InterseccionEntrePuntoySprite1(Sprite SpriteAVerificar, Float puntoXAVerificar, Float puntoYAVerificar) {
            Boolean HayInterseccion = false;
            //Determino los bordes de cada Sprite
            Float SpArriba, SpAbajo, SpDerecha, SpIzquierda;
            SpArriba = SpriteAVerificar.getPositionY() + SpriteAVerificar.getHeight() / 2;
            SpAbajo = SpriteAVerificar.getPositionY() - SpriteAVerificar.getHeight() / 2;
            SpDerecha = SpriteAVerificar.getPositionX() + SpriteAVerificar.getWidth() / 2;
            SpIzquierda = SpriteAVerificar.getPositionX() - SpriteAVerificar.getWidth() / 2;

            if(puntoXAVerificar<=SpIzquierda && puntoXAVerificar<=SpDerecha && puntoYAVerificar>=SpAbajo && puntoYAVerificar<=SpArriba){
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

            if(puntoXAVerificar<=SpIzquierda && puntoXAVerificar<=SpDerecha && puntoYAVerificar>=SpAbajo && puntoYAVerificar<=SpArriba){
                HayInterseccion = true;
            }
            return HayInterseccion;
        }

    }
}
    /////////////////////////////////////////////////////////////
