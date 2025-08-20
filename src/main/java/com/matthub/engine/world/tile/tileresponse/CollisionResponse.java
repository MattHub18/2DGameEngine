package com.matthub.engine.world.tile.tileresponse;

import com.matthub.engine.entities.Entity;
import com.matthub.engine.physics.collision.AxisAlignedBoundingBox;
import com.matthub.engine.physics.geom.Vector2D;
import com.matthub.engine.world.World;
import com.matthub.engine.world.tile.Tile;

public class CollisionResponse implements TileResponse {
    @Override
    public void apply(Entity player, Tile tile, World world, Object value) {
        AxisAlignedBoundingBox playerBox = player.getBox();

        AxisAlignedBoundingBox tileBox = getTileBox(tile);

        if (playerBox.collisionWithAABB(tileBox)) {
            AxisAlignedBoundingBox overlap = (AxisAlignedBoundingBox) playerBox.intersectWithAABB(tileBox);
            if (overlap != null) {
                float overlapW = overlap.getMax().x - overlap.getMin().x;
                float overlapH = overlap.getMax().y - overlap.getMin().y;

                Vector2D correction;

                if (overlapW < overlapH) {
                    // push horizontally
                    if (playerBox.getCenter().x < tileBox.getCenter().x)
                        correction = new Vector2D(-overlapW, 0); // push left
                    else
                        correction = new Vector2D(overlapW, 0);  // push right
                } else {
                    // push vertically
                    if (playerBox.getCenter().y < tileBox.getCenter().y)
                        correction = new Vector2D(0, -overlapH); // push up
                    else
                        correction = new Vector2D(0, overlapH);  // push down
                }
                player.setPosition(player.getPosition().add(correction));
            }
        }
    }

    private AxisAlignedBoundingBox getTileBox(Tile tile) {
        int tileX = (int) tile.getPosition().x;
        int tileY = (int) tile.getPosition().y;
        int tileWidth = tile.getTileWidth();
        int tileHeight = tile.getTileHeight();
        return new AxisAlignedBoundingBox(new Vector2D(tileX, tileY), new Vector2D((tileX+1) * tileWidth, (tileY+1) * tileHeight));
    }
}
