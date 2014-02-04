package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Implementation of hex coordinates called "Cube coordinates".
 * <p/>
 * http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
 * http://www.redblobgames.com/grids/hexagons/
 */
public class HexCoords3 {

    static final float H = (float) Math.sqrt(3) / 2;
    static final float H3 = (float) 1;

    /**
     * Matrix that projects cube hex coordinates into x, y orthogonal (graphic) plane coordinates.
     */
    final Matrix4 projection = new Matrix4();

    final Matrix4 projectionInverse = new Matrix4();

    /**
     * Virtual cube vectors - each cube coordinate (x, y, z) is the factor of its appropriate base vector.
     */
    final Vector3 x;
    final Vector3 y;
    final Vector3 z;

    HexCoords3() {
        x = new Vector3(H, -0.5f, 0);
        y = new Vector3(0, 1f, 0);
//        z = new Vector3(-H, -0.5f, 0);
        z = new Vector3().sub(x).sub(y);
        // imaginary z coordinate is used for inverse projection only
        x.z = y.z = z.z = H3;

//        initializing projection matrix with its base vectors
        projection.val[Matrix4.M00] = x.x;
        projection.val[Matrix4.M10] = x.y;
        projection.val[Matrix4.M20] = x.z;

        projection.val[Matrix4.M01] = y.x;
        projection.val[Matrix4.M11] = y.y;
        projection.val[Matrix4.M21] = y.z;

        projection.val[Matrix4.M02] = z.x;
        projection.val[Matrix4.M12] = z.y;
        projection.val[Matrix4.M22] = z.z;

        projectionInverse.set(projection.val);
        projectionInverse.inv();
    }

    Vector3[] getHexVerts() {
        return getHexVerts(1);
    }

    Vector3[] getHexVerts(int size) {

        return new Vector3[]{
                new Vector3(size, 0, 0),
                new Vector3(size, size, 0),
                new Vector3(0, size, 0),
                new Vector3(0, size, size),
                new Vector3(0, 0, size),
                new Vector3(size, 0, size),
        };

    }

    void drawHex(Vector3 origin, ShapeRenderer shapeRenderer) {

        Vector3 o = new Vector3(origin);

        project(o);

        Vector3[] verts = getHexVerts();

        float[] polys = new float[verts.length * 2];

        for (int i = 0; i < verts.length; i++) {

//             projection.;

            project(verts[i]);
            verts[i].add(o);

            polys[2 * i] = verts[i].x;
            polys[2 * i + 1] = verts[i].y;
        }

        shapeRenderer.polyline(polys);
    }

    void drawHexGrid(ShapeRenderer shapeRenderer) {
        int w = 6;
        int h = 4;

        for (float rd = 0; rd < h; rd++) {
            for (float coli = 0; coli < w; coli++) {
//                int c0 = 0;
                int c0 = 0 - (int) rd / 2;
                float col = c0 + coli;

                shapeRenderer.setColor(col % 2, rd % 2, (-col - rd) % 2, 1);
//                drawHex(2 * col + rd, 2 * rd + col, shapeRenderer);
//                drawHex(new Vector3(2 * col + rd, 2 * rd + col, 0), shapeRenderer);
//                drawHex(new Vector3(-2 * col + rd, -2 * rd + col, 0), shapeRenderer);
                drawHex(new Vector3(col, rd, -col - rd), shapeRenderer);
//                drawHex(new Vector3(2 * col, 2 * rd, -2 * col - 2 * rd), shapeRenderer);
            }
        }

        for (int y = 0; y < h * 2; y++) {
            for (int x = 0; x < w * 2; x++) {
                shapeRenderer.setColor(x % 2, y % 2, (-x - y) % 2, 1);
                Vector3 v = new Vector3(x, y, 0);
                project(v);
                int s = (int) v.x + (int) v.y + (int) v.z;
                switch (s % 2) {
                    case 0:
                        shapeRenderer.setColor(Color.WHITE);
                        break;
                    case 1:
                        shapeRenderer.setColor(Color.BLUE);
                        break;
                    case -1:
                        shapeRenderer.setColor(Color.GREEN);
                        break;
                    default:
                        shapeRenderer.setColor(Color.RED);
                        break;
                }
//                shapeRenderer.setColor(Color.WHITE);
                shapeRenderer.circle(v.x, v.y, 0.2f);
            }
        }

    }

    private void project(Vector3 uv) {
        uv.mul(projection);
    }

    void unproject(float screenx, float screeny, Vector3 outCube) {
        outCube.set(screenx, screeny, 0);
        // unproject
        outCube.mul(projectionInverse);
    }

    float z(Vector2 vec) {
        return z(vec.x, vec.y);
    }

    float z(float x, float y) {
        return -x - y;
    }

    /**
     * @param u num of column to right
     * @param v num of right upwards diagonal to right
     * @return vector in imaginary coordinates x, y, z
     */
    Vector2 toXyz(int u, int v) {
        return new Vector2(2 * u + v, 2 * v + u);
    }

    float distance(Vector2 v1, Vector2 v2) {
        Vector3 dir = new Vector3(v2.x - v1.x, v2.y - v1.y, z(v2) - z(v1));
        return Math.max(Math.max(dir.x, dir.y), dir.z);
    }

    // TODO there is better implementation of round that always returns center of hex - this does not
    public static Vector3 round(Vector3 round) {
        return round.set(MathUtils.round(round.x), MathUtils.round(round.y), MathUtils.round(round.z));
    }

}
