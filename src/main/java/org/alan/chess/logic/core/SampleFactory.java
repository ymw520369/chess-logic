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
     * 向工厂中添加一个模板
     *
     * @param sample
     */
    void addSample(T sample);

    /**
     * 向工厂中批量添加模板
     *
     * @param samples
     */
    void addSamples(List<T> samples);

    /**
     * 向工厂中批量添加模板，并在添加之前先将原来的模板全部清除
     *
     * @param samples
     */
    void reloadSamples(List<T> samples);

    /**
     * 新创建一个指定sid的模板对象，采用java默认的克隆方式，如果对象需要深度克隆，则必须自己实现clone方法
     *
     * @param sid
     * @return
     */
    T newSample(int sid);

    /**
     * 获取一个指定sid的模板源对象，获取该对象应该用于内容只读，任何需要对模板进行修改都应该使用
     * {@link SampleFactory#newSample(int)}
     * </p>
     *
     * @param sid
     * @return
     */
    T getSample(int sid);

    /**
     * 获取该工厂中所有模板
     *
     * @return 模板集合
     */
    Collection<T> getAllSamples();
}
