import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { motion, AnimatePresence } from 'framer-motion';
import { Sprout, MapPin, Droplets, Activity, RefreshCw } from 'lucide-react';

const API_BASE = 'http://localhost:8081/api';

function App() {
  const [farms, setFarms] = useState([]);
  const [recommendations, setRecommendations] = useState({});
  const [formData, setFormData] = useState({ latitude: '', longitude: '', soilType: 'Loamy', cropType: 'Wheat' });

  const fetchData = async () => {
    try {
      const { data } = await axios.get(`${API_BASE}/farms`);
      setFarms(data);
      const recs = {};
      for (const farm of data) {
        const recRes = await axios.get(`${API_BASE}/recommendations/${farm.id}`);
        if (recRes.data) recs[farm.id] = recRes.data;
      }
      setRecommendations(recs);
    } catch (err) { console.error(err); }
  };

  useEffect(() => {
    fetchData();
    const timer = setInterval(fetchData, 5000);
    return () => clearInterval(timer);
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await axios.post(`${API_BASE}/farms`, { ...formData, userId: 1 });
    fetchData();
  };

  return (
    <div className="max-w-6xl mx-auto px-4 py-12">
      <h1 className="text-4xl font-bold gradient-text flex items-center gap-3 mb-8">
        <Sprout size={40} /> AgroSmart AI Dashboard
      </h1>
      
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="glass p-8 rounded-2xl h-fit">
          <h2 className="text-xl font-bold mb-4">Register Farm</h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <input className="input-field" placeholder="Latitude" onChange={e => setFormData({...formData, latitude: e.target.value})} />
            <input className="input-field" placeholder="Longitude" onChange={e => setFormData({...formData, longitude: e.target.value})} />
            <button className="btn-primary w-full">Analyze with AI</button>
          </form>
        </div>

        <div className="lg:col-span-2 space-y-6">
          {farms.map(farm => (
            <div key={farm.id} className="glass p-8 rounded-2xl flex justify-between items-center">
              <div>
                <h3 className="text-xl font-bold">Farm #{farm.id}</h3>
                <p className="text-text-muted">{farm.soilType} • {farm.cropType}</p>
              </div>
              <div className="flex gap-4">
                <div className="bg-emerald-500/10 p-4 rounded-xl">
                  <p className="text-xs font-bold text-emerald-400 uppercase">Irrigation</p>
                  <p className="font-semibold">{recommendations[farm.id]?.irrigationAdvice || 'Analyzing...'}</p>
                </div>
                <div className="bg-blue-500/10 p-4 rounded-xl">
                  <p className="text-xs font-bold text-blue-400 uppercase">Fertilizer</p>
                  <p className="font-semibold">{recommendations[farm.id]?.fertilizerSuggestion || 'Syncing...'}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default App;
