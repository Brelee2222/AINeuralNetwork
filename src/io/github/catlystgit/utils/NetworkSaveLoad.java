package io.github.catlystgit.utils;

import io.github.catlystgit.Catlyst.CatlystNeuralNetwork;
import io.github.catlystgit.Network.NeuralNetwork;
import io.github.catlystgit.Neuron.Neuron;
import io.github.catlystgit.Neuron.NeuronInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class NetworkSaveLoad {
    /**
     * Save format
     * 1. inputs (int)
     * 2. layers (int)
     * 3. layerSize (int) * layers
     * 4. Neuron inputs (double)...
     */

    private static int index;
    private static byte[] data;

    public static void save(NeuralNetwork network, String path) {
        int size = 8 + network.layers.length*4;
        for(int i = 0; i != network.layers.length; i++) for(Neuron neuron : network.layers[i])
            size += neuron.getInputs().length*8;
        data = new byte[size];
        index = 0;
        writeInt(network.inputs);
        writeInt(network.layers.length);
        for(Neuron[] layer : network.layers)
            writeInt(layer.length);
        for(Neuron[] layer : network.layers) for(Neuron neuron : layer) for(NeuronInput input : neuron.getInputs())
            writeLong(Double.doubleToLongBits(input.getWeight()));
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            fileOutputStream.write(data);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static NeuralNetwork read(String path) {
        try {
            data = Files.readAllBytes(new File(path).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        index = 0;
        int inputs = readInt();
        int layers = readInt();

        int[] layerSizes = new int[layers];
        int prevLayer = inputs;
        for(int i = 0; i != layers; i++)
            layerSizes[i] = readInt();
        CatlystNeuralNetwork network = new CatlystNeuralNetwork(inputs, layerSizes, 0, 0);
        for(Neuron[] layer : network.layers) for(Neuron neuron : layer) for(NeuronInput neuronInput : neuron.getInputs())
            neuronInput.setWeight(Double.longBitsToDouble(readLong()));
        return network;
    }

    public static byte readByte() {
        return data[index++];
    }
    public static short readShort() {
        return (short) ((short) readByte() << 8 | readByte() & 0xff);
    }
    public static int readInt() {
        return (int) readShort() << 16 | Short.toUnsignedInt(readShort());
    }
    public static long readLong() {
        return (long) readInt() << 32 | readInt() & 0xffffffffL;
    }

    public static void writeByte(byte value) {
        data[index++] = value;
    } // could've made a universal writeFUnction
    public static void writeShort(short value) {
        writeByte((byte) (value >> 8));
        writeByte((byte) value);
    }
    public static void writeInt(int value) {
        writeShort((short) (value >> 16));
        writeShort((short) value);
    }
    public static void writeLong(long value) {
        writeInt((int) (value >> 32));
        writeInt((int) value);
    }
}
