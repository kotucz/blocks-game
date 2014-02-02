package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class HexStage extends BaseStage {

    HexCoords hexCoords = new HexCoords();

    HexCoords hexCoords2 = new HexCoords2();

    HexCoords3 hexCoords3 = new HexCoords3();

    final Array<Hex> hexes = new Array<Hex>();
    //    GenericGrid<Square> grid = new GenericGrid<Square>(new LinearGrid(12, 8));
    private Hex follower;

    ShapeRenderer shapeRenderer;
    private Vector3 touch = new Vector3();


    void init() {
        super.init();

        // populate grid
//        for (LinPos p : grid.getLinGrid()) {
//            grid.set(p.i, new Square());
//        }
        shapeRenderer = new ShapeRenderer();

//        {
//            Hex block = new Hex().setPos(2.3f, 3);
//            hexes.add(block);
//        }
//        {
        follower = new Hex();
        hexes.add(follower);
//        }
//        {
//            Slider slider = new Slider();
//            slider.textureRegion = T.blockTextureRegion.get(3);
//            slider.setPos(5, 6.4f);
//            slider.target.set(5, 6);
//            hexes.add(slider);
//        }
//
//        {
//            Slider slider = new Slider();
//            slider.textureRegion = T.blockTextureRegion.get(3);
//            slider.setPos(5, 6.4f);
//            slider.target.set(2, 6);
//            hexes.add(slider);
//        }

    }

    void update() {

        for (Hex block : hexes) {
            block.update();
        }


    }

    private <T extends Hex> Iterable<T> getHexsOfType(final Class<T> clss) {
        Predicate<Hex> instanceOfPredicate = new Predicate<Hex>() {
            @Override
            public boolean evaluate(Hex block) {
                return clss.isInstance(block);
            }
        };
        return (Iterable<T>) new Predicate.PredicateIterable<Hex>(hexes, instanceOfPredicate);
    }

    void draw(Matrix4 combined) {
//        batch1.getProjectionMatrix();

        shapeRenderer.setProjectionMatrix(combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1, 1, 0, 1);

//        shapeRenderer.line(x, y, x2, y2);
        shapeRenderer.rect(1, 1, 1, 1);
//        shapeRenderer.circle(x, y, radius);

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1, 1, 0, 1);

        shapeRenderer.polyline(new float[]{1, 1, 2, 2});

//        Vector2[] verts = new Vector2[]{
//                new Vector2(0, 0),
//                new Vector2(1, 0),
//                new Vector2(0, 1),
//        };

//        hexCoords.drawHexGrid(this.shapeRenderer);
//        hexCoords2.drawHexGrid(this.shapeRenderer);
//        hexCoords3.drawHex(0, 0, shapeRenderer);
        hexCoords3.drawHexGrid(this.shapeRenderer);

//        shapeRenderer.rect(1, 1, 1, 1);
//        shapeRenderer.circle(x, y, radius);

        shapeRenderer.setColor(1, 1, 1, 1);

        hexCoords3.drawHex(follower.pos, shapeRenderer);

        hexCoords3.drawHex(touch, shapeRenderer);

        hexCoords3.drawHex(round(touch.cpy()), shapeRenderer);

        shapeRenderer.end();

//        for (LinPos p : grid.getLinGrid()) {
//            final Square square = grid.get(p.i);
//
//            final int neighHash = GridUtils.getNeighHash(p, new Predicate<Pos>() {
//                @Override
//                public boolean evaluate(Pos pos) {
//                    final Square nsquare = grid.get(pos);
//                    if (nsquare == null) {
//                        return false;
//                    }
////                    return (pos.x + pos.y) % 2 == 0;
//                    return nsquare.count > 0;
//                }
//            });
//
//            if (square.count > 0) {
//
//                final Sprite sprite = T.blockSprites[neighHash];
//
//                sprite.setPosition(p.x, p.y);
//
//                sprite.draw(batch1);
//            } else {
//                int image = neighHash / 4;
//
////            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 0.5f, 0.5f, 1, 1, 1, 1, MathUtils.random(8)* 45);
////            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 0.5f, 0.5f, 1, 1, 1, 1, 45);
//                batch1.draw(T.blockTextureRegion.get(image), p.x, p.y, 1, 1);
//
//            }
//        }

        for (Hex block : hexes) {
//            block.draw(shapeRenderer);
        }

        batch.setProjectionMatrix(combined.cpy().scl(1 / 16f));

        batch.begin();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
//        decimalFormat.format(1.199); //"1.2"

//        font.setScale();
        {
            font.draw(batch, "x: " + decimalFormat.format(touch.x) +
                    " y: " + decimalFormat.format(touch.y) +
                    " z: " + decimalFormat.format(touch.z) +
                    " s: " + decimalFormat.format(touch.x + touch.y + touch.z), 0, 10 * 16);
        }
        {
            font.draw(batch, "x: " + decimalFormat.format(follower.pos.x) +
                    " y: " + decimalFormat.format(follower.pos.y) +
                    " z: " + decimalFormat.format(follower.pos.z) +
                    " s: " + decimalFormat.format(follower.pos.x + follower.pos.y + follower.pos.z), 0, 9 * 16);
        }

        batch.end();

    }

    public void pointerDown(float x1, float y1) {
//        final int x = MathUtils.floor(x1);
//        final int y = MathUtils.floor(y1);
//        follower.target.set(x, y);
//        final Square square = grid.get(x, y);
//        if (square != null) {
//            square.count++;
//        }
    }

    final Map<Integer, Slider> draggedMap = new HashMap<Integer, Slider>();

    public boolean touchDown(float x, float y, int pointer, int button) {
//        for (Slider slider : getHexsOfType(Slider.class)) {
//            if (slider.getRect().contains(x, y)) {
////                final Vector2 v = new Vector2();
////                slider.getRect().getCenter(v);
////                v.sub(x, y);
////                slider.target.add(v);
//                draggedMap.put(pointer, slider);
//            }
//        }
        return true;
    }

    public boolean touchDragged(float screenx, float screeny, int pointer) {
        Vector3 screen = new Vector3(screenx, screeny, 0);
        float x = hexCoords3.x.dot(screen);
//        x = Math.max(0, x);
//        screen.sub(hexCoords3.x.cpy().scl(x));
        float y = hexCoords3.y.dot(screen);
//        y = Math.max(0, y);
//        screen.sub(hexCoords3.y.cpy().scl(y));
        float z = hexCoords3.z.dot(screen);
//        z = Math.max(0, z);
//        screen.sub(hexCoords3.z.cpy().scl(z));
//        float z = hexCoords3.z.dot(screen);
//        float y = hexCoords3.y.dot(screen.sub(hexCoords3.x.cpy().scl(x)));
//        float z = hexCoords3.z.dot(screen.sub(hexCoords3.x.cpy().scl(x)).sub(hexCoords3.y.cpy().scl(y)));
//                float z = 0;
//        touch.set(x, y, hexCoords3.z(x, y));
        touch.set(x, y, z);
        touch.scl(2 / 3f);
        return false;
    }

    public boolean touchUp(float x, float y, int pointer, int button) {
        final Slider slider = draggedMap.get(pointer);

        if (slider != null) {
            final int fx = MathUtils.floor(x);
            final int fy = MathUtils.floor(y);
            slider.target.set(fx, fy);
            draggedMap.remove(pointer);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        switch (character) {
            case 'd':
                follower.pos.x++;
                follower.pos.z--;
                break;
            case 'e':
                follower.pos.y++;
                follower.pos.z--;
                break;
            case 'w':
                follower.pos.y++;
                follower.pos.x--;
                break;
            case 'a':
                follower.pos.z++;
                follower.pos.x--;
                break;
            case 'z':
                follower.pos.z++;
                follower.pos.y--;
                break;
            case 'x':
                follower.pos.x++;
                follower.pos.y--;
                break;
        }
        return super.keyTyped(character);
    }

    public static Vector3 round(Vector3 round) {
        return round.set(MathUtils.round(round.x), MathUtils.round(round.y), MathUtils.round(round.z));
    }

}
