package de.toxicfox.block;

public record Configuration(boolean touchMode, boolean persistWorld, int renderDistance, String worldFolder,
                            boolean captureCursor) {
}
