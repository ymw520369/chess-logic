package org.alan.chess.logic.core;

import java.util.Collection;
import java.util.List;

/**
 * Created on 2017/7/11.
 *
 * @author Alan
 * @since 1.0
 */
public interface SampleFactory<T> {
    /**
     * �򹤳������һ��ģ��
     *
     * @param sample
     */
    void addSample(T sample);

    /**
     * �򹤳����������ģ��
     *
     * @param samples
     */
    void addSamples(List<T> samples);

    /**
     * �򹤳����������ģ�壬�������֮ǰ�Ƚ�ԭ����ģ��ȫ�����
     *
     * @param samples
     */
    void reloadSamples(List<T> samples);

    /**
     * �´���һ��ָ��sid��ģ����󣬲���javaĬ�ϵĿ�¡��ʽ�����������Ҫ��ȿ�¡��������Լ�ʵ��clone����
     *
     * @param sid
     * @return
     */
    T newSample(int sid);

    /**
     * ��ȡһ��ָ��sid��ģ��Դ���󣬻�ȡ�ö���Ӧ����������ֻ�����κ���Ҫ��ģ������޸Ķ�Ӧ��ʹ��
     * {@link SampleFactory#newSample(int)}
     * </p>
     *
     * @param sid
     * @return
     */
    T getSample(int sid);

    /**
     * ��ȡ�ù���������ģ��
     *
     * @return ģ�弯��
     */
    Collection<T> getAllSamples();
}
